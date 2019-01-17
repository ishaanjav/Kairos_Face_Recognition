
<img align="right" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Identification.gif" width="315">

# Kairos_Face_Recognition

**The purpose of this Android app is to use Kairos's SDK for Android in order to implement facial recognition. Kairos's API offers one of the simplest methods to integrate such features into any Android app.** 

Features of this app include: 
- **Registering users** given their image and name.
- **Identifying users** when given their image.

You can view more about the features of Kairos's API [on their website](https://www.kairos.com/features). They used to have a GitHub repository with their Android SDK where it contained information about using Kairos in Android, however it seems like they removed that repository. You can find the equivalent information below.

<br/>
<br/>
<br/>

-----

<img align="left" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Full%20Process.gif" width="311">

## Usage:

**Please note that in order to use this app, you MUST get the API Key and App ID from the [Kairos API Dashboard](https://developer.kairos.com/admin). In the Setup section, you will find the steps on how to request a free trial of the API and use it in the app**.


The app consists of two buttons, an `EditText` and an `ImageView`. The two buttons are used for the **enrollment and recognition processes** by taking a picture of a person. That picture is then displayed in an `ImageView`.
- **Enrollment Process:** The `EditText` is used to enter the name of the person being enrolled. The `Enroll` button is then used to launch the camera and register the person with their name along with the image of their face.
- **Identification Process:** The `Identify` button is used to launch the camera and identify the person in the image. It uses the Kairos API to output the result of the person with the closest matching face. If it can't identify the person, then it returns an empty `String`.

<br/>

-----
## Setup
