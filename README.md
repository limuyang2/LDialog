[![](https://jitpack.io/v/limuyang2/LDialog.svg)](https://jitpack.io/#limuyang2/LDialog)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


# LDialog

### [中文](https://github.com/limuyang2/LDialog/README_CN.md)

A library based on Google's recommended DialogFragment package, according to its own business extraction package, the library is written using ```kotlin```, ```java``` can also be called, can meet most project needs, can Used in Activity and Fragment. The guiding principle of this project is to adhere to the maximum degree of freedom.  

The library currently has the following features:  

* Rotate the vertical and vertical screens to save the Dialog property state (and maintain the event state of the DialogFragment, such as a click event)
* Complete custom interface
* Rich interface property settings
* Perfect keyboard automatically pops up (not using delay method)

> Recommendations：
> DialogFragment has many advantages over AlertDialog. However, for simple information prompts, only native styles, and no horizontal and vertical rotation is required, a simpler AlertDialog is recommended. Please don't complicate the simple question.  

> Source description：  
> If you haven't gotten started with koltin, it is recommended to learn to use it. The environment version of this library is as follows：  
> * kotlin 1.2.61
> * Android support 27.1.1

## Preview
![](https://github.com/limuyang2/LDialog/blob/master/screenshot/kap.gif)
> Due to the limitations of the screen. Please download the demo experience

### Demo download link
[demo apk](https://github.com/limuyang2/LDialog/blob/master/apk/app-release.apk)

## Obtain
This library is divided into the necessary ```LDialog`` and non-essential ```CustomLDialog```.
```LDialog``` is the base library; ```CustomLDialog``` contains custom styles, and you don't need to import them if you don't need them.
First add in the repositories of build.gradle : 
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add in dependencies：  
```gradle
dependencies {
	//Must be imported
	implementation 'com.github.limuyang2.LDialog:ldialog:1.0'
	//3 custom styles, you don't need to import them if you don't need them
	implementation 'com.github.limuyang2.LDialog:custom_ldialog:1.0'
}
```

## Simple to use
Both LDialog and CustonLDialog inherit from the BaseLDialog class.  

>The parameters in init(), ```Activity``` use ```supportFragmentManager```, ```Fragment``` using ```childFragmentManager```  

### CustonLDialog Use

There are currently 3 custom styles:
* IOSMsgDialog
* MaterialMsgDialog
* BottomTextListDialog

The following is an example of ```MaterialMsgDialog```:
```kotlin
//koltin
MaterialMsgDialog.init(supportFragmentManager) //Freagment using childFragmentManager
	.setTitle("Material Style")
	.setMessage("This is Material Design dialog!")
	.setNegativeButton("Decline", View.OnClickListener {
		Toast.makeText(this@MainActivity, "Decline", Toast.LENGTH_SHORT).show()
    })
    .setPositiveButton("Accept", View.OnClickListener {
        Toast.makeText(this@MainActivity, "Accept", Toast.LENGTH_SHORT).show()
    })
    .show()
```
```java
//java
MaterialMsgDialog.Companion.init(getSupportFragmentManager())
	.setTitle("Material Style")
	.setMessage("This is Material Design dialog!")
	.setNegativeButton("Decline", new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(JavaDemo.this, "Decline", Toast.LENGTH_SHORT).show()
        	}
    	})
    	.setPositiveButton("Accept", new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
            		Toast.makeText(JavaDemo.this, "Accept", Toast.LENGTH_SHORT).show()
        	}
    	})
    	.show();
```

### LDialog use (mainly used)
Used to provide a custom layout using layoutRes.  
Examples：

```kotlin
//kotlin
LDialog.init(supportFragmentManager)
	.setLayoutRes(R.layout.ldialog_share)
	.setBackgroundDrawableRes(R.drawable.shape_share_dialog_bg)
	.setGravity(Gravity.BOTTOM)
	.setWidthScale(0.95f)
	.setVerticalMargin(0.015f)
	.setAnimStyle(R.style.LDialogBottomAnimation)
	.setViewHandlerListener(object : ViewHandlerListener() {
		override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
        	holder.setOnClickListener(R.id.cancelBtn, View.OnClickListener {
            	dialog.dismiss()
            })
       }
    })
    .show()
```
[For Java usage, please refer to JavaDemo.java under the project.](https://github.com/limuyang2/LDialog/blob/master/app/src/main/java/top/limuyang2/ldialogdemo/java/JavaDemo.java)

### Method description

| Method name                   | Description                                                         |
| ------------------------ | ------------------------------------------------------------ |
| setLayoutRes             | Set layout resources 【priority is higher than setLayoutView】【LDialog only, must】 |
| setLayoutView            | Set layout view (not recommended)【LDialog only，must】                |
| * setViewHandlerListener | (Important) Set the properties of the controls in the layout. If need to consider horizontal and vertical rotation, the relevant properties of the control must be set here. It is recommended that the settings for the controls in the layout be written here.【LDialog only, must】 |
|||
| General method（BaseLDialog） |                                                              |
|setBackgroundDrawableRes|Dialog Background resource file id|
| setTag                   | DialogFragment tag                                       |
| setDismissListener       | Dialog off Listener                                           |
| setGravity               | Dialog Gravity（Examples：Gravity.CENTER    Gravity.TOP）        |
| setWidthScale            | Proportion of screen width(range 0.0 - 1.0, when it is 1.0, it is full)【Priority is higher than ```setWidthDp```】 |
|setWidthDp|Dialog width, unit dp|
|setHeightScale|Proportion of screen height (range 0.0 - 1.0)【Priority is higher than ```setHeightDp```】|
|setHeightDp|Dialog height, unit dp|
|setKeepWidthScale|Whether to maintain the set width ratio when horizontally【Only takes effect when the width ratio ```setWidthScale``` is set】(default false)|
|setKeepHeightScale|Whether to maintain the set height ratio when the screen is horizontal【Only takes effect when the height ratio ```setHeightScale``` is set】(default false)|
|setVerticalMargin|Set the Margin value in the vertical direction (range 0.0 - 0.1)|
|setCancelableAll|Set whether you can click outside the dialog and Back key to close the dialog|
|setCancelableOutside|Set whether you can click outside the dialog to close the dialog (the Back key is not affected)|
|setAnimStyle|Animated Style resource file id|
|setNeedKeyboardEditTextId|Set the control id that needs to automatically pop up the keyboard. It must be a control of type EditText.|
|show|Show Dialog|

## Advanced use
If the above still can not meet your needs, you can directly inherit the ```BaseLDialog``` class, and also have a general method. For details, please refer to the three Dialog classes in ```CustonLDialog``.  
The basic writing is as follows：
```kotlin
class ExKotlinLdialog : BaseLDialog<ExKotlinLdialog>() {

    override fun layoutRes(): Int = R.layout.ldialog_share

    override fun layoutView(): View? = null

    /**
     * Must
     * If [need] to consider horizontal and vertical rotation, the relevant properties of the control are set here.
     * @return
     */
    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                
            }
        }
    }

    /**
     * Optional
     * If you don't consider the horizontal and vertical rotation, you can also set the control properties here.
     * @param view
     */
    override fun initView(view: View) {

    }
}
```
[Java use please refer to ExJavaLdialog.java under the project](https://github.com/limuyang2/LDialog/blob/master/app/src/main/java/top/limuyang2/ldialogdemo/java/ExJavaLdialog.java)


## License
```
2018 limuyang
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
