# Student Scheduler Android App
This Java-written Android mobile app allows users to add, edit, and delete information related to their school terms, the courses within these terms, and the assessments within these courses. This information is stored via an internal database written in SQLite and can be viewed throughout the app's UI. The user also has the ability to add and edit notes for each course and email these notes via their phone's preferred emailing app. Furthermore, the user has the ability to set notifications directly into their phone for upcoming course start and end dates and assessment due dates.

## App Navigation

#### The user is greeted by the app's home screen, displaying information about upcoming courses and assessments.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Home%20Screen.PNG?raw=true" width="250">


#### The user may open up the app's main navigation drawer by clicking the hamburger button in the top-left of the screen.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Drawer%20Screen.PNG?raw=true" width="250">


#### Upon clicking the 'Terms' option in the nav drawer, a recycler view list of the user's terms appears. The user must add his/her own terms to be displayed on the screen by clicking the 'Add a Term' floating action button. Terms can also be deleted by swiping its element to the left. However, a term cannot be deleted if it has any assigned courses to it, preserving data integrity.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Terms%20Screen.PNG?raw=true" width="250">


#### A new activity appears after clicking the button, allowing the user to input information about a new term, which will be added to the previous screen's recycler view.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Add%20Term%20Screen.PNG?raw=true" width="250">


#### From the 'Your Terms' screen, the user can view detailed information about each term by clicking one of the recyler view's elements, showing courses for the chosen term. Courses must be added to the term by clicking the 'Add a course for this term' floating action button. Courses can also be deleted by swiping its element to the left, or edited by swiping it to the right.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Term%20Courses%20Screen.PNG?raw=true" width="250">


#### A new activity appears after clicking the button, allowing the user to input information about a new course, which will be added to the previous screen's recycler view. The user may also set an alert for this course into their phone, which will send a push notification to them on the dates of which it starts and ends.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Add%20Course%20Screen.PNG?raw=true" width="250">


#### A course can also be edited by swiping it to the right, which brings up a new activity that auto-fills all of that course's current information. The course can then be easily adjusted and saved with its new information.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Edit%20Course%20Screen.PNG?raw=true" width="250">


#### More information about each course can be viewed by clicking on its element. This screen displays information for its course mentor, a button to view its unique notes, a button allowing the user to email the stored notes, as well as information for its assessments. Assessments can be deleted by swiping its element to the left, or edited by swiping it to the right.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Course%20Assessments%20Screen.PNG?raw=true" width="250">


#### Clicking on the 'Show Notes' button display an editable dialog screen. The user is required to click the save button to get back to the detailed course view.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Notes%20Screen.PNG?raw=true" width="250">


#### Clicking on the 'Email Notes' button pops up an option to email the course's notes by using a separate emailing application already installed on the user's device.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Email%20Notes%20Screen.PNG?raw=true" width="250">


#### Assessments can be added to this course by clicking on the floating action button next to the Assessments title. The user may set a notification for this assessment in their phone to display on its due date.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Add%20Assessment%20Screen.PNG?raw=true" width="250">


#### An assessment can also be edited by swiping it to the right, which brings up a new activity that auto-fills all of that assessment's current information. The assessment can then be easily adjusted and saved with its new information.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20Edit%20Assessment%20Screen.PNG?raw=true" width="250">


#### Going back to the navigation drawer, a full list of all of the user's courses from all terms can be viewed upon clicking 'Courses'. The user may edit a course from this screen, but no courses can be added without first going into a term.

<img src="https://github.com/mattjoslen/StudentSchedulerAndroidApp/blob/master/images/App%20All%20Courses%20Screen.PNG?raw=true" width="250">
