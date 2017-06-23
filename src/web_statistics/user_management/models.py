#coding: utf-8

from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
from django.contrib import admin
from django.contrib.auth.models import User

# Create your models here.

class Person(models.Model):
    # user = models.ForeignKey(User, on_delete=models.CASCADE)
    username = models.CharField(verbose_name="Username:", max_length=32, blank=True)
    last_name = models.CharField(verbose_name="Фамилия:", max_length=32)
    first_name = models.CharField(verbose_name="Имя:", max_length=32)
    # middle_name = models.CharField(verbose_name="Отчество:", max_length=32)
    login = models.CharField(verbose_name="Ваш Логин:", max_length=32)
    password = models.CharField(verbose_name="Пароль для доступа к ЛК:", max_length=32)
    confirmpassword = models.CharField(verbose_name="Подтвердите пароль:", max_length=32)
    email = models.EmailField(verbose_name="E-mail:", max_length=64)
    phone_number = PhoneNumberField(verbose_name="Контактный номер:", blank=True)
    # male = 'Мужской'
    # female = 'Женский'
    # genders = (
    #     (male, 'Мужской'),
    #     (female, 'Женский'),
    # )
    # gender = models.CharField(verbose_name="Ваш пол:", max_length=32, choices=genders)
    # birthdate = models.DateField(verbose_name="Дата рождения:", blank=True)
    # country = models.CharField(verbose_name="Ваша страна:", max_length=32)
    # city = models.CharField(verbose_name="Ваш город/населённый пункт:", max_length=32)
    user_administrator = 'Администратор'
    user_user = 'Пользователь'
    types_of_users = (
        (user_administrator, 'Администратор'),
        (user_user, 'Пользователь'),
    )
    status = models.CharField(max_length=32, verbose_name="Выберите Ваш статус пользователя:", choices=types_of_users,
                              default=user_user)
    # location = GeopositionField(blank=True)
    photo = models.ImageField(verbose_name="Загрузите Ваше главное фото:", upload_to="static/img/avatars/", name="photo", blank=True)
    rules = models.BooleanField(max_length=32)

    def __str__(self):
        return self.login

    # @receiver(post_save, sender=User)
    # def create_user_profile(sender, instance, created, **kwargs):
    #     if created:
    #         Person.user.create(user=instance)
    #
    # @receiver(post_save, sender=User)
    # def save_user_profile(sender, instance, **kwargs):
    #     instance.person.save()

admin.site.register(Person)
