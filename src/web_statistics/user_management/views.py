# import table as table
from django.contrib import auth
from django.contrib.auth.models import User
from django.shortcuts import render, HttpResponseRedirect
from django.http import Http404
from django.contrib.auth.models import User
from user_management.models import Person
from django.core.exceptions import ValidationError
from user_management.forms import MyRegistrationForm

import table as table
from django.contrib import auth
from django.contrib.auth.models import User
from django.shortcuts import render, HttpResponseRedirect
from django.http import Http404
from django.contrib.auth.models import User
from user_management.models import Person
from django.core.exceptions import ValidationError
from user_management.forms import MyRegistrationForm
# from .forms import UploadFileForm
# from UploadFileForm import handle_uploaded_file

# Create your views here.
def login(request):
    if request.method == 'POST':
        print("POST data =", request.POST)
        username = request.POST.get('login')
        password = request.POST.get('password')
        user = auth.authenticate(username=username, password=password)
        print('login -> user =', user)
        if user is not None:
            auth.login(request, user)
            return HttpResponseRedirect("/privateroom/")    # /privateroom/
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
        middle_name = request.POST.get("middle_name")
        login = request.POST.get("login")
        password = request.POST.get("password")
        confirmpassword = request.POST.get("confirmpassword")
        email = request.POST.get("email")
        phone_number = request.POST.get("phone_number")
        gender = request.POST.get("gender")
        birthdate = request.POST.get("birthdate")
        country = request.POST.get("country")
        district = request.POST.get("district")
        city = request.POST.get("city")
        your_photo = request.POST.get("photo")
        your_status = request.POST.get("status")
        print(request.POST)
        user = User()
        user.username = 'asdasd'
        user.set_password('asdasdf')
        # Validate data
        if password != confirmpassword:
            errors['password'] = 'Извините, пароли не совпадают... Попробуйте снова!'
        p = Person(user=user, email=email, first_name=first_name,
                      last_name=last_name, middle_name=middle_name, phone_number=phone_number, gender=gender,
                      birthdate=birthdate, country=country, district=district, city=city,
                      your_photo=your_photo, your_status=your_status)
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


def registration1(request):
    if request.method == 'POST':
        form = MyRegistrationForm(request.POST, request.FILES)
        if form.is_valid():
            cd = form.cleaned_data
            table.your_photo = cd['your_photo']      #'value_from_datadict'
            form.save()
            print("===FORM SAVED!!!!!===")
            return HttpResponseRedirect('/privateroom/')
        else:
            print("===FORM INVALID!!!!!===")
        context = {'form': form}
        return render(request, 'registration.html', context)
    context = {'form': MyRegistrationForm()}
    return render(request, 'registration.html', context)