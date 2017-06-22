from django.shortcuts import render
from tag_manager.models import Persons, Keywords
from source_manager.models import Sites, Pages
from django.contrib.auth.decorators import login_required


def main(request):
    return render(request, 'index.html')


def statistics(request):
    return render(request, 'statistics.html')

# @login_required(login_url='/privateroom/')
# def common_statistics(request):
#     sites = Sites.objects.order_by('name')
#     persons = Persons.objects.order_by('name')
#     keywords = Keywords.objects.order_by('name')
#     return render(request, "incl_daily_st.html", {'persons': persons, 'keywords': keywords, 'sites': sites})


@login_required(login_url='/privateroom/')
def daily_statistics(request):
    sites = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    keywords = Keywords.objects.order_by('name')
    return render(request, 'daily_statistics.html', {'persons': persons, 'keywords': keywords, 'sites': sites})


def periodic_statistics(request):
    return render(request, 'periodic_statistics.html')


def registration(request):
    return render(request, 'registration.html')


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
