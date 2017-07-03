#coding: utf-8

from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
from django.contrib import admin
from django.contrib.auth.models import User


def handle_uploaded_file(f):
    destination = open('../media/img/avatars/avatar.jpg', 'wb+')
    for chunk in f.chunks():
        destination.write(chunk)
    destination.close()


class Person(models.Model):
    # person = models.ForeignKey(User, on_delete=models.CASCADE)
    username = models.CharField(verbose_name="Username:", max_length=32, blank=True)
    last_name = models.CharField(verbose_name="Фамилия:", max_length=32)
    first_name = models.CharField(verbose_name="Имя:", max_length=32)
    login = models.CharField(verbose_name="Ваш Логин:", max_length=32)
    password = models.CharField(verbose_name="Пароль для доступа к ЛК:", max_length=32)
    confirmpassword = models.CharField(verbose_name="Подтвердите пароль:", max_length=32)
    email = models.EmailField(verbose_name="E-mail:", max_length=64)
    phone_number = PhoneNumberField(verbose_name="Контактный номер:", blank=True)
    birthdate = models.DateField(verbose_name="Дата рождения:", blank=True, default=None)
    user_administrator = 'Администратор'
    user_user = 'Пользователь'
    types_of_users = (
        (user_administrator, 'Администратор'),
        (user_user, 'Пользователь'),
    )
    status = models.CharField(max_length=32, verbose_name="Выберите Ваш статус пользователя:", choices=types_of_users,
                              default=user_user)
    photo = models.ImageField(verbose_name="Загрузите Ваше главное фото:", upload_to="../media/img/avatars/", name="photo", blank=True)
    rules = models.BooleanField(max_length=32)

    def __str__(self):
        return self.login