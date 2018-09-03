[![](https://jitpack.io/v/limuyang2/LDialog.svg)](https://jitpack.io/#limuyang2/LDialog)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# LDialog
一个基于Google推荐的DialogFragment封装的的库，根据自身业务提取封装，本库全部使用```kotlin```编写，```java```亦可调用，能满足大部分的项目需求，能在Activity与Fragment中使用。本项目准则即是遵守最大化的自由程度。  

本库目前已具备的特点如下：

* 横竖屏旋转保存Dialog属性状态（并且能保持DialogFragment的事件状态，例如点击事件）
* 完全的自定义界面
* 丰富的界面属性设置
* 完美的键盘自动弹出(并非使用延迟的方法)

> 使用建议：
> DialogFragment相对于AlertDialog有很多优点。但对于只需要非常简单信息提示、仅需要原生样式，以及不考虑横竖屏的情况下，推荐使用更简单的AlertDialog，请不要把简单问题复杂化。DialogFragment适合用有UI要求、使用要求的情况下。  

> 源码说明：  
> 如果你还没上手koltin，建议学习使用。本库的环境版本如下：  
> * kotlin 1.2.51
> * Android support 27.1.1

## 预览
![](https://github.com/limuyang2/LDialog/blob/master/screenshot/kap.gif)
> 由于录屏的限制无法录制横竖切换情况。请下载demo体验

### demo下载地址
[demo apk](https://github.com/limuyang2/LDialog/blob/master/apk/app-release.apk)

## 获取
本库分为必须导入的```LDialog```和非必须的```CustomLDialog```。  
```LDialog```为基础库；```CustomLDialog```中包含了自定义的样式，不需要可以不导入。  
先在 build.gradle 的 repositories 添加：  
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

再在dependencies添加：  
```gradle
dependencies {
	//必须导入
	implementation 'com.github.limuyang2.LDialog:ldialog:1.0'
	//3种自定义样式，不使用就不导入
	implementation 'com.github.limuyang2.LDialog:custom_ldialog:1.0'
}
```

## 简单使用
LDialog与CustonLDialog均继承于BaseLDialog类。  

>init()中的参数，```Activity```中使用```supportFragmentManager```，```Fragment```中使用```childFragmentManager```  

### CustonLDialog 使用方式

目前里面包含3种自定义样式：
* IOSMsgDialog
* MaterialMsgDialog
* BottomTextListDialog

以下以```MaterialMsgDialog```为示例：
```kotlin
//koltin
MaterialMsgDialog.init(supportFragmentManager) //Freagment中使用childFragmentManager
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

### LDialog 使用方式 (主要使用方式)
用于对外提供使用 layoutRes 自定义布局。  
示例如下：
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
[Java使用请参考项目下的 JavaDemo.java](https://github.com/limuyang2/LDialog/blob/master/app/src/main/java/top/limuyang2/ldialogdemo/java/JavaDemo.java)

### 方法说明

| 方法名                   | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ |
| setLayoutRes             | 设置布局资源【优先级高于setLayoutView】【仅LDialog，必须】 |
| setLayoutView            | 设置布局view（不推荐使用）【仅LDialog，必须】                |
| * setViewHandlerListener | （重要）设置布局中控件的属性。如果【需要】考虑横竖屏旋转，则控件的相关属性必须在此设置。建议对布局中控件的设置均写在此处。【仅LDialog，必须】 |
|||
| 通用方法（BaseLDialog） |                                                              |
|setBackgroundDrawableRes|弹窗背景资源文件id|
| setTag                   | DialogFragment的标签                                         |
| setDismissListener       | 弹窗的关闭监听                                            |
| setGravity               | 窗体位置（例：Gravity.CENTER    Gravity.TOP）                |
| setWidthScale            | 占屏幕宽度的比例（范围0.0 - 1.0，当为1.0时即为铺满）【优先级高于setWidthDp】 |
|setWidthDp|Dialog的宽度，单位dp|
|setHeightScale|占屏幕高度的比例（范围0.0 - 1.0）【优先级高于setHeightDp】|
|setHeightDp|Dialog的高度，单位dp|
|setKeepWidthScale|横屏时，是否保持设置的宽度比例【仅当设置了宽度比例setWidthScale后，才生效】（默认false）|
|setKeepHeightScale|横屏时，是否保持设置的高度比例【仅当设置了高度比例setHeightScale后，才生效】（默认false）|
|setVerticalMargin|设置垂直方向的Margin值（范围0.0 - 0.1）|
|setCancelableAll|设置是否可以点击dialog外及返回键关闭dialog|
|setCancelableOutside|设置是否可以点击dialog外关闭dialog（返回键不受影响）|
|setAnimStyle|动画Style资源文件id|
|setNeedKeyboardEditTextId|设置需要自动弹出键盘的控件id，必须是EditText类型的控件|
|show|显示Dialog|

## 高级使用
如果以上仍然无法满足你们的需求，那可以直接继承```BaseLDialog```类，同时也就具备了通用方法。具体可参考```CustonLDialog```中的三个弹窗类。  
基本写法如下：
```kotlin
class ExKotlinLdialog : BaseLDialog<ExKotlinLdialog>() {

    override fun layoutRes(): Int = R.layout.ldialog_share

    override fun layoutView(): View? = null

    /**
     * 必须
     * 如果【需要】考虑横竖屏旋转，则控件的相关属性在此设置
     * @return
     */
    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                
            }
        }
    }

    /**
     * 可选
     * 如果【不】考虑横竖屏旋转，也可以在此设置控件属性
     * @param view
     */
    override fun initView(view: View) {

    }
}
```
[Java使用请参考项目下的 ExJavaLdialog.java](https://github.com/limuyang2/LDialog/blob/master/app/src/main/java/top/limuyang2/ldialogdemo/java/ExJavaLdialog.java)


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
