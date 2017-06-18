from django.contrib import admin


# Register your models here.

class PersonAdmin(admin.ModelAdmin):
    list_display = ['login']

# admin.site.register(Person, PersonAdmin)