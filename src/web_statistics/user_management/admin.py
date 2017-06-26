from django.contrib import admin
from .models import Person


# Register your models here.

class PersonAdmin(admin.ModelAdmin):
    list_display = ['username', 'last_name', 'first_name', 'login', 'email', 'phone_number', 'status']

admin.site.register(Person, PersonAdmin)
