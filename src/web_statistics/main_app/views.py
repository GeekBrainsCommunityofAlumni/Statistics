from django.shortcuts import render
from tag_manager.models import Persons, Keywords, PersonPageRank
from source_manager.models import Sites, Pages
from django.contrib.auth.decorators import login_required
from django.db.models import Sum
from pprint import pprint


def main(request):
    return render(request, 'headpage.html')


def about(request):
    return render(request, 'index.html')


def statistics(request):
    return render(request, 'statistics.html')


@login_required(login_url='/privateroom/')
def daily_statistics(request):
    sites = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    return render(request, 'daily_statistics.html', {'persons': persons, 'keywords': keywords,
                                                     'sites': sites, 'person_ranks': person_ranks})


@login_required(login_url='/privateroom/')
def periodic_statistics(request):
    sites = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    return render(request, 'periodic_statistics.html', {'persons': persons, 'keywords': keywords,
                                                        'sites': sites, 'person_ranks': person_ranks})


def registration(request):
    return render(request, 'registration.html')

# def registration(request):
#     username = Person.objects.order_by('name')
#     last_name = Person.objects.order_by('name')
#     first_name = Person.objects.order_by('name')
#     login = Person.objects.order_by('name')
#     email = Person.objects.order_by('name')
#     phone_number = Person.objects.order_by('name')
#     status = Person.objects.order_by('name')
#     return render(request, 'registration.html', {'username': username, 'last_name': last_name, 'first_name': first_name, 'login': login, 'email': email, 'phone_number': phone_number, 'status': status})



def privateroom(request):
    return render(request, 'privateroom.html')


def authorization(request):
    return render(request, 'authorization.html')


def partnership(request):
    return render(request, 'partnership.html')


def contacts(request):
    return render(request, 'contacts.html')


def review(request):
    return render(request, 'review.html')


def support(request):
    return render(request, 'support.html')
