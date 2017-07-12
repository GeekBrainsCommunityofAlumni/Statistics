from django.shortcuts import render
from tag_manager.models import Persons, Keywords, PersonPageRank
from source_manager.models import Sites, Pages
from django.contrib.auth.decorators import login_required
from django.db.models import Sum
from UserManagement.models import Person as User
from django.http import HttpResponseRedirect
from .forms import ParametrizedStatForm
from django.http import Http404
import requests
import json

API = 'http://94.130.27.143:8080/api'


def main(request):
    return render(request, 'headpage.html')


def about(request):
    return render(request, 'index.html')


def sitemap(request):
    return render(request, 'sitemap.html')


@login_required(login_url='/privateroom/')
def periodic_statistics(request):
    sites = Sites.objects.order_by('name')
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    persons_all = Persons.objects.all()
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))

    if request.method == 'POST':
        form = ParametrizedStatForm(request.POST)
        site_id = request.POST.get('source')
        profile_id = request.POST.get('profile')

        # default page data, or search all pages and all persons
        if site_id == 'all' and profile_id == 'all':
            person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
        # search specific site
        elif site_id != 'all' and profile_id == 'all':
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.values('person_id_id', 'person_id_id'). \
                    filter(page_id_id__id=site_id).annotate(rank=Sum('rank'))
        # search specific person
        elif site_id == 'all' and profile_id != 'all':
            person_ranks = PersonPageRank.objects.values('page_id_id', 'person_id_id'). \
                filter(person_id_id=profile_id).annotate(rank=Sum('rank'))
            persons = Persons.objects.all().filter(id=profile_id)
        # search both parameters: site, person
        else:
            persons = Persons.objects.all().filter(id=profile_id)
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.filter(person_id_id=profile_id) \
                .values('page_id_id', 'person_id_id').annotate(rank=Sum('rank'))

        return render(request, "periodic_statistics.html", {'sites': sites, 'persons': persons,
                                                            'keywords': keywords, 'form': form,
                                                            'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected, 'persons_all':persons_all})
    else:

        return render(request, 'periodic_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                            'keywords': keywords,
                                                            'sites': sites, 'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected})


def faq(request):
    return render(request, 'faq.html')


# def statistics(request):
#     return render(request, 'statistics.html')


@login_required(login_url='/privateroom/')
def common_statistics(request):
    sites = Sites.objects.order_by('name')
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    persons_all = Persons.objects.all()
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))

    if request.method == 'POST':
        form = ParametrizedStatForm(request.POST)
        site_id = request.POST.get('source')
        profile_id = request.POST.get('profile')

        # default page data, or search all pages and all persons
        if site_id == 'all' and profile_id == 'all':
            person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
        # search specific site
        elif site_id != 'all' and profile_id == 'all':
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.values('person_id_id', 'person_id_id'). \
                filter(page_id_id__site_id_id=site_id).annotate(rank=Sum('rank'))
        # search specific person
        elif site_id == 'all' and profile_id != 'all':
            person_ranks = PersonPageRank.objects.values('page_id_id', 'person_id_id'). \
                filter(person_id_id=profile_id).annotate(rank=Sum('rank'))
            persons = Persons.objects.all().filter(id=profile_id)
        # search both parameters: site, person
        else:
            persons = Persons.objects.all().filter(id=profile_id)
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.filter(person_id_id=profile_id) \
                .values('page_id_id', 'person_id_id').annotate(rank=Sum('rank'))

        return render(request, "common_statistics.html", {'sites': sites, 'persons': persons,
                                                            'keywords': keywords, 'form': form,
                                                            'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected,
                                                            'persons_all': persons_all})
    else:

        return render(request, 'common_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                            'keywords': keywords,
                                                            'sites': sites, 'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected})


@login_required(login_url='/privateroom/')
def daily_statistics(request):
    keywords = requests.get(API + '/keyword').json()
    persons = requests.get(API + '/person').json()
    sites = requests.get(API + '/site').json()
    sites_selected = Sites.objects.order_by('name')
    #persons = Persons.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))

    if request.method == 'POST':
        form = ParametrizedStatForm(request.POST)
        site_id = request.POST.get('source')
        profile_id = request.POST.get('profile')

        # default page data, or search all pages and all persons
        if site_id == 'all' and profile_id == 'all':
            person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
        # search specific site
        elif site_id != 'all' and profile_id == 'all':
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.values('person_id_id', 'person_id_id'). \
                filter(page_id_id__site_id_id=site_id).annotate(rank=Sum('rank'))
        # search specific person
        elif site_id == 'all' and profile_id != 'all':
            person_ranks = PersonPageRank.objects.values('page_id_id', 'person_id_id'). \
                filter(person_id_id=profile_id).annotate(rank=Sum('rank'))
            persons = Persons.objects.all().filter(id=profile_id)
        # search both parameters: site, person
        else:
            persons = Persons.objects.all().filter(id=profile_id)
            sites_selected = Sites.objects.all().filter(id=site_id)
            person_ranks = PersonPageRank.objects.filter(person_id_id=profile_id) \
                .values('page_id_id', 'person_id_id').annotate(rank=Sum('rank'))

        return render(request, "daily_statistics.html", {'sites': sites, 'persons': persons,
                                                            'keywords': keywords, 'form': form,
                                                            'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected})
    else:

        return render(request, 'daily_statistics.html', {'persons': persons, 'keywords': keywords,
                                                            'sites': sites, 'person_ranks': person_ranks,
                                                            'sites_selected': sites_selected})


@login_required(login_url='/privateroom/')
def admin_statistics(request):
    sites = requests.get(API + '/site').json()
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    keywords = Keywords.objects.order_by('name')
    keywords_all = requests.get(API + '/keyword').json()
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    users = User.objects.all()

    if request.method == 'POST':
        person_name = request.POST.get('new_person')
        print(person_name)
        r = requests.post(API + '/person', json={'name': person_name},
                          headers={"Content-Type": "application/json"})
        print('USER CREATION:', r.status_code)

    persons_all = requests.get(API + '/person').json()

    return render(request, 'admin_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                     'keywords': keywords, 'sites': sites, 'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'users': users})


@login_required(login_url='/privateroom/')
def admin_del_person(request):
    sites = requests.get(API + '/site').json()
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    keywords = Keywords.objects.order_by('name')
    keywords_all = requests.get(API + '/keyword').json()
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    users = User.objects.all()

    if request.method == 'POST':
        person_id = request.POST.get('source')
        r = requests.delete(API + '/person/' + person_id)
        print('USER REMOVED:', r.status_code)

    persons_all = requests.get(API + '/person').json()

    return render(request, 'admin_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                     'keywords': keywords, 'sites': sites, 'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'users': users})


@login_required(login_url='/privateroom/')
def admin_add_site(request):
    sites_selected = Sites.objects.order_by('name')
    persons_all = requests.get(API + '/person').json()
    keywords_all = requests.get(API + '/keyword').json()
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    users = User.objects.all()
    if request.method == 'POST':
        site_name = request.POST.get('site_name')
        r = requests.post(API + '/site', json={'name': site_name},
                          headers={"Content-Type": "application/json"})
        print('SITE CREATION:', r.status_code)

    sites = requests.get(API + '/site').json()

    return render(request, 'admin_statistics.html', {'persons_all': persons_all, 'sites': sites,
                                                     'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'users': users})


@login_required(login_url='/privateroom/')
def admin_del_site(request):
    sites_selected = Sites.objects.order_by('name')
    persons_all = requests.get(API + '/person').json()
    keywords_all = requests.get(API + '/keyword').json()
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    users = User.objects.all()
    if request.method == 'POST':
        site_id = request.POST.get('source')
        r = requests.delete(API + '/site/' + site_id)
        print('Site REMOVED:', r.status_code)

    sites = requests.get(API + '/site').json()

    return render(request, 'admin_statistics.html', {'persons_all': persons_all, 'sites': sites,
                                                     'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'users': users})


@login_required(login_url='/privateroom/')
def admin_keyword(request):
    sites = requests.get(API + '/site').json()
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    persons_all = requests.get(API + '/person').json()
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    pages = Pages.objects.all()
    users = User.objects.all()
    if request.method == 'POST':
        person_id = request.POST.get('source')
        keyword_name = request.POST.get('keyword')
        r = requests.post(API + '/keyword/' + person_id, json={'name': keyword_name},
                          headers={"Content-Type": "application/json"})
        print('KEYWORD CREATION:', r.status_code)

    keywords_all = requests.get(API + '/keyword').json()

    return render(request, 'admin_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                     'keywords': keywords, 'sites': sites, 'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'pages': pages, 'users': users})


@login_required(login_url='/privateroom/')
def admin_del_keyword(request):
    sites = requests.get(API + '/site').json()
    sites_selected = Sites.objects.order_by('name')
    persons = Persons.objects.order_by('name')
    persons_all = requests.get(API + '/person').json()
    keywords = Keywords.objects.order_by('name')
    person_ranks = PersonPageRank.objects.values('person_id_id', 'page_id_id').annotate(rank=Sum('rank'))
    pages = Pages.objects.all()
    users = User.objects.all()
    if request.method == 'POST':
        keyword_id = request.POST.get('source')
        r = requests.delete(API + '/keyword/' + keyword_id)
        print('USER REMOVED:', r.status_code)

    keywords_all = requests.get(API + '/keyword').json()

    return render(request, 'admin_statistics.html', {'persons': persons, 'persons_all': persons_all,
                                                     'keywords': keywords, 'sites': sites, 'person_ranks': person_ranks,
                                                     'sites_selected': sites_selected, 'keywords_all': keywords_all,
                                                     'pages': pages, 'users': users})


def politics(request):
    return render(request, 'politics.html')


def economics_and_finances(request):
    return render(request, 'economics_and_finances.html')


def business(request):
    return render(request, 'business.html')


def society(request):
    return render(request, 'society.html')


def api_stat(request):
    # todo: request http://94.130.27.143:8080/api/ with parameters, and send json into the template
    return render()


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

def googlechart(request):
    return render(request, 'googlechart.html')