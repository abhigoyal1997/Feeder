from django.conf.urls import url

from . import views

urlpatterns = [
	url(r'^$',views.login, name='login'),
	url(r'^register/',views.register,name='register'),
	url(r'^signup/',views.signup, name='signup'),
	url(r'^authadmin$',views.auth_admin,name='auth_admin'),
	url(r'^authinstruct$',views.auth_inst,name='auth_inst'),
	url(r'^admin_home$',views.admin_home,name='admin_home'),	
	url(r'^ins_home$',views.ins_home,name='ins_home'),
	url(r'^logout$',views.logout_view,name='logout'),
	url(r'^students$',views.student_list,name='student_list'),
	url(r'^instructors$',views.instructor_list,name='instructor_list'),
	url(r'^courses$',views.course_list,name='course_list'),
	url(r'^addcourse$',views.add_course,name='add_course'),
	url(r'^admin_profile$',views.admin_profile,name='admin_profile'),
	url(r'^update_admin$',views.update_admin,name='update_admin'),
	url(r'^createcourse$',views.make_course,name='make_course'),
	url(r'^addfeedback$',views.add_feedback,name='add_feedback'),
	url(r'^adddeadline$',views.add_deadline,name='add_deadline'),
	url(r'^viewdeadline$',views.view_deadline,name='view_deadline'),
	url(r'^viewfeedback$',views.view_feedback,name='view_feedback'),
	url(r'^createfeedback$',views.make_feedback,name='make_feedback'),
	url(r'^createdeadline$',views.make_deadline,name='make_deadline'),
	url(r'^instructor_profile$',views.instprofile,name='instprofile'),
	url(r'^addsttocourse/(?P<code>[A-Z]{2}[-][0-9]{3})$',views.add_stud_to_course,name='add_stud_to_course'),
	url(r'^ajax/modify/$',views.modify,name='modify'),
	url(r'^fb_login/$',views.fb_login,name='fb_login'),
	url(r'^viewresponses/(?P<feedbackid>\w{0,50})$',views.viewresponses,name='viewresponses'),	
	url(r'^viewobjective/(?P<feedbackid>\w{0,50})$',views.viewobjective,name='viewobjective'),	
]