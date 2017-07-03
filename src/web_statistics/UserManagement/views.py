#coding: utf-8

from django.contrib import auth
from django.shortcuts import render, render_to_response
from django.http import Http404, HttpResponseRedirect
# from django.template.context_processors import csrf
from django.contrib.auth.models import User
from UserManagement.models import handle_uploaded_file, Person
from django.core.exceptions import ValidationError
from .forms import UploadFileForm
# from .forms import MyRegistrationForm


def upload_file(request):
    if request.method == 'POST':
        form = UploadFileForm(request.POST, request.FILES)
        if form.is_valid():
            handle_uploaded_file(request.FILES['file'])
            return HttpResponseRedirect('/success/url/')
    else:
        form = UploadFileForm()
    return render_to_response('upload.html', {'form': form})


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
        # user = User()
        # user.username = 'Admin'
        # user.set_password('testgbca')
        # Validate data
        if password != confirmpassword:
            errors['password'] = 'Извините, пароли не совпадают... Попробуйте снова!'
        user = User(username=login, password=password, email=email, first_name=first_name, last_name=last_name)
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
