from django.contrib import admin

from django.contrib import admin
from source_manager.models import Pages
from tag_manager.models import PersonPageRank


class PagesAdmin(admin.ModelAdmin):
    list_display = ['url', 'site_id', 'last_scan_date']


class PersonPageRankAdmin(admin.ModelAdmin):
    list_display = ['person_id', 'page_id', 'rank']


admin.site.register(Pages, PagesAdmin)
admin.site.register(PersonPageRank, PersonPageRankAdmin)
