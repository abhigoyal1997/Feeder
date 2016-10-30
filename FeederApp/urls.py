from django.conf.urls import url

from . import views

urlpatterns = [
	url(r'^$',views.login, name='login'),
	url(r'^register/',views.register,name='register'),
	url(r'^signup/',views.signup, name='signup'),
	url(r'^authadmin$',views.auth_admin,name='auth_admin'),
	url(r'^authinstruct$',views.auth_inst,name='auth_inst'),
	url(r'^admin_home$',views.admin_home,name='admin_home'),	
	url(r'^admin$',views.admin_login,name='admin_login'),	
	url(r'^ins_home$',views.ins_home,name='ins_home'),
	url(r'^logout$',views.logout_view,name='logout'),
	url(r'^students$',views.student_list,name='student_list'),
	url(r'^instructors$',views.instructor_list,name='instructor_list'),
	url(r'^courses$',views.course_list,name='course_list'),
	url(r'^addcourse$',views.add_course,name='add_course'),
	url(r'^admin_profile$',views.admin_profile,name='admin_profile'),
	url(r'^update_admin$',views.update_admin,name='update_admin'),
	url(r'^createcourse$',views.make_course,name='make_course'),
	url(r'^remove_stdcourse/(?P<username>.*)&(?P<coursecode>[A-Z]{2}[\s][0-9]{3})$',views.remove_stdcourse,name='remove_stdcourse'),
	url(r'^remove_inscourse/(?P<username>.*)&(?P<coursecode>[A-Z]{2}[\s][0-9]{3})$',views.remove_inscourse,name='remove_inscourse')	
]