from django.db import models
from source_manager.models import Pages


class Persons(models.Model):
    name = models.CharField(max_length=2048, blank=False)

    def __str__(self):
        return self.name


class Keywords(models.Model):
    name = models.CharField(verbose_name='key_word', max_length=2048, blank=False)
    person_id = models.ForeignKey(Persons)

    def __str__(self):
        return self.name


class PersonPageRank(models.Model):
    person_id = models.ForeignKey(Persons)
    page_id = models.ForeignKey(Pages)
    rank = models.IntegerField(blank=True)

    def __str__(self):
        return self.rank
