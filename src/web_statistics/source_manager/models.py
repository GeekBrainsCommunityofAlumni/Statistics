from django.db import models
from django.contrib import admin


class Sites(models.Model):
    name = models.CharField(max_length=2048, blank=False)

    def __str__(self):
        return self.name


class Pages(models.Model):
    url = models.CharField(max_length=2048)
    site_id = models.ForeignKey(Sites)
    found_date_time = models.DateTimeField()
    last_scan_date = models.DateTimeField()

    def __str__(self):
        return self.url


admin.site.register(Sites)
