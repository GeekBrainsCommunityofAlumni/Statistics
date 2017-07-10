#coding: utf-8

from django import forms
from UserManagement.models import Person
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm


class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=50)
    file = forms.FileField()


class MyRegistrationForm(forms.ModelForm):
    username = forms.CharField(max_length=32, widget=forms.TextInput(attrs={"type": "text", "class": "form-control", "placeholder": "Username"}))
    last_name = forms.CharField(max_length=32, widget=forms.TextInput(attrs={"type": "text", "class": "form-control", "placeholder": "Фамилия"}))
    first_name = forms.CharField(max_length=32, widget=forms.TextInput(attrs={"type": "text", "class": "form-control", "placeholder": "Имя"}))
    login = forms.CharField(max_length=32, widget=forms.TextInput(attrs={"type": "text", "class": "form-control", "placeholder": "логин"}))
    password = forms.CharField(max_length=32, widget=forms.PasswordInput(attrs={"type": "password", "class": "form-control", "placeholder": "пароль"}))
    confirmpassword = forms.CharField(max_length=32, widget=forms.PasswordInput(attrs={"type": "password", "class": "form-control", "placeholder": "Пожалуйста, подтвердите..."}))
    email = forms.CharField(max_length=32, widget=forms.EmailInput(attrs={"type": "email", "class": "form-control", "placeholder": "e-mail"}))
    phone_number = forms.CharField(max_length=32, widget=forms.NumberInput(attrs={"type": "number", "class": "form-control", "placeholder": "phone_number"}))
    birthdate = forms.CharField(widget=forms.DateInput(attrs={"type": "date", "id": "birthdate", "class": "form-control"}))
    status = forms.CharField(widget=forms.Select(attrs={"id": "status", "class": "form-control"}))
    photo = forms.FileField(widget=forms.ImageField())  # {"value_from_datadict": ""}
    rules = forms.BooleanField(widget=forms.CheckboxInput(attrs={"type": "checkbox"}))

    class Meta:
        model = Person
        fields = ('__all__')
        exclude = ('rules',)


class UserForm(forms.ModelForm):
    class Meta:
        model = User
        fields = ('__all__')
        exclude = ('rules',)


class UserChangeForm(forms.ModelForm):
    """
    Форма для обновления данных пользователей. Нужна только для того, чтобы не
    видеть постоянных ошибок "Не заполнено поле password" при обновлении данных
    пользователя.
    """

    class Meta:
        model = Person
        fields = ('__all__')
        exclude = ('rules',)



class EditProfileForm(UserChangeForm):
    """
    Форма для обновления данных пользователей.
    """

    class Meta:
        model = Person
        fields = (
            'username',
            'first_name',
            'last_name',
            'email'
        )
