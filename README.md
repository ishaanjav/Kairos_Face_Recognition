
<img align="right" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Identification.gif" width="315">

# Kairos Face Recognition

**The purpose of this Android app is to use Kairos's SDK for Android in order to implement facial recognition. Kairos's API offers one of the simplest methods to integrate such features into any Android app.** 

Features of this app include: 
- **Registering users** given their image and name.
- **Identifying users** when given their image.

You can view more about the features of Kairos's API [on their website](https://www.kairos.com/features). They used to have a GitHub repository with their Android SDK where it contained information about using Kairos in Android, however it seems like they removed that repository. You can find the equivalent information below in the **[Additional Information Section](#additional-information).**

<br/>
<br/>
<br/>

-----

<img align="left" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Full%20Process.gif" width="311">

## Usage

**Please note that in order to use this app, you MUST get the API Key and App ID from the [Kairos API Dashboard](https://developer.kairos.com/admin). In the Setup section, you will find the steps on how to request a free trial of the API and use it in the app**.


The app consists of two buttons, an `EditText` and an `ImageView`. The two buttons are used for the **enrollment and recognition processes** by taking a picture of a person. That picture is then displayed in an `ImageView`.
- **Enrollment Process:** The `EditText` is used to enter the name of the person being enrolled. The `Enroll` button is then used to launch the camera and register the person with their name along with the image of their face.
- **Identification Process:** The `Identify` button is used to launch the camera and identify the person in the image. It uses the Kairos API to output the result of the person with the closest matching face. If it can't identify the person, then it returns an empty `String`.

<br/>
<br/>
<br/>

-----
## Setup
The steps below cover how to get the API Key from Kairos through a free trial and then use the key in the app in order for it to function as expected.
### Downloading to Android Studio
To use the app, you can clone it from this GitHub repository as a zip file, extract the contents of the file, and open it as a project in Android Studio. Once you have done so, it can be run on your Android device.
### Getting the Kairos free trial
Unless you already have a Kairos pricing plan that you are paying for, you will have to create an account on [Kairos's Website](https://github.com/ishaanjav/Kairos_Face_Recognition/edit/master/README.md) which can be [done here](https://github.com/ishaanjav/Kairos_Face_Recognition/edit/master/README.md). The page should look similar to the image below:

<img align="center" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Kairos%20Free%20Trial.PNG" width="900">

To get the free trial, you will have to fill in the required fields to create your account after which you will have to confirm the account through the email that you have provided. **The email that you provide must be a *non-disposable* email, meaning that it cannot end in something like `@gmail.com`.** 

### Getting the API Key and App ID
Upon getting the [free trial](#getting-the-kairos-free-trial), you can log into Kairos's website at [this link](https://developer.kairos.com/login). You can then access the [API Dashboard](https://developer.kairos.com/admin) at [this link](https://developer.kairos.com/admin) and copy the API Key and App ID which will be used in the app in the following step. You should see something similar to the image below.

<img align="center" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/API%20Dashboard.PNG" width="900">

###### **Please note that if you are using the free trial, you will only receive one API Key and one App ID. What this means is that since you are on a free trial, you will only be able to use the Kairos API in one app. For [Kairos's Pricing Plans](https://www.kairos.com/pricing), you can check out [this link](https://www.kairos.com/pricing) which has plans starting at $99 per month and $0.02 per transaction. *Custom plans are available upon request.*** 

### Using the API Key in the app
Now that you have received the [API Key and App ID](#getting-the-api-key-and-app-id), you simply have to change 2 lines of code to use the app. **If you go to `MainActivity.java`, you should see the following lines of code above `onCreate`:**

    //IMPORTANT ------------------------------------------------------------------------------
    //Replace the following with the ID and Key provided to you at the Kairos API Dashboard - https://developer.kairos.com/admin
    //...
      
    String app_id = "<YOUR APP ID COMES HERE>";
    String api_key = "<YOUR API KEY COMES HERE>";
    
    //IMPORTANT ------------------------------------------------------------------------------
Replace `app_id`'s value of `<YOUR APP ID COMES HERE>` with the App ID that you got from the [API Dashboard](https://developer.kairos.com/admin). Then, replace `<YOUR API KEY COMES HERE>`with the API Key from the [API Dashboard](https://developer.kairos.com/admin). Below is an example of what those two lines of code should look like now:
    
    //Please note that this is just an example and you will have to substitute the example with your own API Key and App ID.
    String app_id = "1111111a";
    String api_key = "123456789abcdefghij123456abcdefg";
    
 -----
 # Additional Information
As I stated at the beginning of the `README`, it looks like Kairos has removed their information about using their SDK for Android. However, **below you can find an explanation of the code that comes from Kairos's Android SDK to understand what is happening and perhaps put it to your own uses.**

### Instantiating a `Kairos` Object: 
The `Kairos` class is used to instantiate a new Kairos object. This object is then used to set authentication of the App ID and API Key as well as call methods such as `recognize` and `enroll`. Below is the relevant code:
 
    //Instantiate a Kairos instance
    Kairos myKairos = new Kairos();
    
    //Set Authentication
    myKairos.setAuthentication(MainActivity.this, app_id, api_key);

### Creating a Kairos Listener:
The `KairosListener` is used to create an `Object` that is passed in to the `recognize` and `enroll` functions, the reason being that is has two methods that can be overriden as shown below:
  
    Kairos identificationListener = new KairosListener() {
        @Override
        public void onSuccess(String response) {
            //The process was successful.
        }

        @Override
        public void onFail(String response) {
            //There was an error while doing the process.
        }
    };

### Recognizing and Enrolling Faces
The `Kairos` class has two methods called `recognize` and `enroll` that recognize and enroll faces, respectively. Below is an example of how to use the two methods:

    //Enrolling a face
    myKairos.enroll(bitmapImage,
                    nameOfPerson,
                    galleryName,
                    selector,
                    multipleFaces,
                    minHeadScale,
                    kairosListener);
                    
    //Identifying a face
    myKairos.recognize(bitmapImage,
                galleryName,
                selector,
                null,
                minHeadScale,
                null,
                kairosListener);
##### When doing `myKairos.recognize`, the `kairosListener` Object will receive a String structured as a JSON in its `onSucess` method as a result of recognizing a face. In order to get the name of the identified person from the JSON, you can look at a function I made in [`MainActivity.java`](https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/app/src/main/java/com/example/anany/kairosfacerecognition/MainActivity.java) called `readJSONForName()` which accepts the JSON String and returns back a String containing solely the name.  

As can be seen above, the main parameters that the methods require are a `Bitmap` of the person's face, their name *(when enrolling)*, and a `KairosListener`. *The other parameters can be null*. As explained in the section above, the `KairosListener` can be used to see whether the task was successful, or whether it failed and its methods can be overriden so that a `Toast` is displayed based on the results of the processes.

-----
# Conclusion
If you have any questions or issues regarding using the Kairos Face Recognition app, you can contact me at ishaanjav@gmail.com or by raising an issue for this repository. Once you have cloned this repository, feel free to make any changes to the app as you wish. After reading the Additional Information section above, you should have an idea of how the recognition and identification processes work. 

If you enjoyed using and testing this app, or feel that you have learned useful information from it, don't be afraid to give it a star. Furthermore, you may also want to check out some of my other repositories for Android apps:

- **[Face Analyzer](https://github.com/ishaanjav/Face_Analyzer)** - This app uses the Microsoft Face API to detect faces in an image and then, for each face detected it generates a thumbnail of that face and displays it in a `ListView` along with relevant information about the face's facial features including *but not limited to*: Estimated Age, Gender, Emotions, Head Positions, and Facial Hair. **[README](https://github.com/ishaanjav/Face_Analyzer/blob/master/README.md)**
- **[Fingerprint Authentication](https://github.com/ishaanjav/Fingerprint_Authentication)** - A simple app that demonstrates how to use a device's fingerprint reader to authenticate a person's finger and identify it among existing fingerprints. **[README](https://github.com/ishaanjav/Fingerprint_Authentication/blob/master/README.md)**

<p align="center">
  <img width="500" height="540" src="https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/Image.jpeg">
</p>

*Please remember that if you are using the Kairos API under a free trial, then you only have 2 weeks to use the API after getting the trial. Afterwards, to use it you have to choose one of Kairos's [pricing plans](https://www.kairos.com/pricing).*

