#coding: utf-8

from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
# from django.db.models.signals import post_save
from django.contrib.auth.models import User


class Person(User):
    user = models.OneToOneField(User)
    login = models.CharField(verbose_name="Ваш Логин:", max_length=32)
    confirmpassword = models.CharField(verbose_name="Подтвердите пароль:", max_length=32)
    phone_number = PhoneNumberField(verbose_name="Контактный номер:", blank=True, default='')
    birthdate = models.DateField(verbose_name="Дата рождения:", blank=True, default='')
    user_administrator = 'Администратор'
    user_user = 'Пользователь'
    types_of_users = (
        (user_administrator, 'Администратор'),
        (user_user, 'Пользователь'),
    )
    status = models.CharField(max_length=32, verbose_name="Выберите Ваш статус пользователя:",
                              choices=types_of_users, default=user_user)
    photo = models.ImageField(verbose_name="Загрузите Ваше главное фото:", upload_to="img/avatars/", name="photo",
                              blank=True)
    rules = models.BooleanField(max_length=32)


# def create_user(sender, **kwargs):
#     if kwargs['created']:
#         user = Person.objects.create(user=kwargs['instance'])
#         # user.save()
#
# post_save.connect(create_user, sender=User)
