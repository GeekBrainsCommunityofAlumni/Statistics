from django.shortcuts import render

# Create your views here.

def main(request):
    return render(request, 'index.html')

def headpage(request):
    return render(request, 'headpage.html')

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