#coding: utf-8

from django.contrib import auth
from django.contrib.auth import update_session_auth_hash
from django.shortcuts import render, redirect
from django.contrib.auth.decorators import login_required
from django.http import Http404, HttpResponseRedirect
from django.contrib.auth.models import User
from django.core.exceptions import ValidationError
from .forms import MyRegistrationForm
# from qsstats import QuerySetStats
from django.contrib.auth.forms import PasswordChangeForm
from UserManagement.forms import EditProfileForm


def login(request):
    if request.method == 'POST':
        print("POST data =", request.POST)
        username = request.POST.get('login')
        password = request.POST.get('password')
        user = auth.authenticate(username=username, password=password)
        print('login -> user =', user)
        if user is not None:
            auth.login(request, user)
            return HttpResponseRedirect("/privateroom/")
        else:
            return render(request, 'privateroom.html', {'username': username, 'errors': True})
    raise Http404


def logout(request):
    if request.method == "POST":
        auth.logout(request)
        return HttpResponseRedirect("/")
    raise Http404


def registration(request):
    if request.method == 'POST':
        form = MyRegistrationForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect("/privateroom/")
    else:
        form = MyRegistrationForm()

        args = {'form': form}
        return render(request, "registration.html")

def registration(request):
    if request.method == 'POST':
        errors = {}  # Тут будем хранить ошибки, чтобы отобразить на странице
        username = request.POST.get("login")
        last_name = request.POST.get("last_name")
        first_name = request.POST.get("first_name")
        login = request.POST.get("login")
        password = request.POST.get("password")
        confirmpassword = request.POST.get("confirmpassword")
        email = request.POST.get("email")
        phone_number = request.POST.get("phone_number")
        birthdate = request.POST.get("birthdate")
        photo = request.POST.get("photo")
        status = request.POST.get("status")
        print(request.POST)
        # user.username = 'Admin'
        # user.set_password('testgbca')
        # Validate data
        if password != confirmpassword:
            errors['password'] = 'Извините, пароли не совпадают... Попробуйте снова!'
        user = User(username=login,
                    password=password,
                    email=email,
                    first_name=first_name,
                    last_name=last_name,
                    status=status,
                    phone_number=phone_number,
                    birthdate=birthdate,
                    photo=photo
                    )
        # Пароли хранятся в виде хэшей, поэтому их нельзя передавать напрямую
        user.set_password(password)
        # Проверяем, существует ли пользователь с таким именем
        try:
            user.validate_unique()
        except ValidationError as er:
            errors.update(er.message_dict)
        # Если есть ошибки, передаем их в контексте шаблону, который умеет их отображать
        if errors:
            return render(request, "registration.html", {"reg_errors": errors})
        # Если ошибок нет, сохраняем пользователя в базе, перенаправляем на главную
        user.save()
        return HttpResponseRedirect("/privateroom/")
    return render(request, "registration.html")


@login_required(login_url='/privateroom/')
def change_password(request):
    if request.method == 'POST':
        form = PasswordChangeForm(data=request.POST, user=request.user)

        if form.is_valid():
            form.save()
            update_session_auth_hash(request, form.user)
            return redirect('/privateroom/')

        else:
            return redirect('/user/change_password/')

    else:
        form = PasswordChangeForm(user=request.user)
        args = {'form': form}
        return render(request, 'change_password.html', args)


@login_required(login_url='/privateroom/')
def edit_profile(request):
    if request.method == 'POST':
        form = EditProfileForm(request.POST, instance=request.user)

        if form.is_valid():
            form.save()
            return redirect('/privateroom/')

        else:
            return redirect('/user/edit_profile/')

    else:
        form = EditProfileForm(instance=request.user)
        args = {'form': form}
        return render(request, 'edit_profile.html', args)
