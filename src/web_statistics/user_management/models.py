#coding: utf-8

from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
from django.contrib import admin
from django.contrib.auth.models import User
#from django.contrib.auth.models import User

# Create your models here.

class Person(models.Model):
    u = models.ForeignKey(User)
    username = models.CharField(verbose_name="Username:", max_length=32, blank=True)
    last_name = models.CharField(verbose_name="Фамилия:", max_length=32)
    first_name = models.CharField(verbose_name="Имя:", max_length=32)
    middle_name = models.CharField(verbose_name="Отчество:", max_length=32)
    login = models.CharField(verbose_name="Ваш Логин:", max_length=32)
    password = models.CharField(verbose_name="Пароль для доступа к ЛК:", max_length=32)
    confirmpassword = models.CharField(verbose_name="Подтвердите пароль:", max_length=32)
    email = models.EmailField(verbose_name="E-mail:", max_length=64)
    phone_number = PhoneNumberField(verbose_name="Контактный номер:", blank=True)
    male = 'Мужской'
    female = 'Женский'
    genders = (
        (male, 'Мужской'),
        (female, 'Женский'),
    )
    gender = models.CharField(verbose_name="Ваш пол:", max_length=32, choices=genders)
    birthdate = models.DateField(verbose_name="Дата рождения:", blank=True)
    country = models.CharField(verbose_name="Ваша страна:", max_length=32)
    city = models.CharField(verbose_name="Ваш город/населённый пункт:", max_length=32)
    user_administrator = 'Администратор'
    user_user = 'Пользователь'
    types_of_users = (
        (user_administrator, 'Администратор'),
        (user_user, 'Потребитель'),
    )
    your_status = models.CharField(max_length=32, verbose_name="Выберите Ваш статус пользователя:", choices=types_of_users,
                              default=user_user)
    # location = GeopositionField(blank=True)
    your_photo = models.ImageField(verbose_name="Загрузите Ваше главное фото:", upload_to="pictures", name="your_photo", blank=True)
    rules = models.BooleanField(max_length=32)

    def __str__(self):
        return self.login

    class Meta:
        db_table = "PersonTables"

admin.site.register(Person)
