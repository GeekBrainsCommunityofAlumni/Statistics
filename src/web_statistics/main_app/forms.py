from django import forms
from source_manager.models import Sites
from tag_manager.models import Persons


class ParametrizedStatForm(forms.ModelForm):
    name = forms.CharField(max_length=2048)

    class Meta:
        model = Sites
        fields = '__all__'


class DelPerson(forms.ModelForm):
    id = forms.IntegerField()

    class Meta:
        model = Persons
        fields = '__all__'


class AddPerson(forms.ModelForm):
    name = forms.CharField()

    class Meta:
        model = Persons
        fields = '__all__'