# material-tip
Android Library for prompt tip ( according to [offer education](https://www.google.com/design/spec/growth-communications/onboarding.html#onboarding-quickstart) )

<!--
  GIT-CARD
  title: Material Tip
  cover: https://dl.dropboxusercontent.com/u/73317452/dev/material_tip.png
  color: #009688
  -->
  
[![](https://jitpack.io/v/fcannizzaro/material-tip.svg)](https://jitpack.io/#fcannizzaro/material-tip)
[![Build Status](https://travis-ci.org/fcannizzaro/material-tip.svg?branch=master)](https://travis-ci.org/fcannizzaro/material-tip)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-material--tip-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3648)
[![git-card](http://goo.gl/2TUzYL)](http://nilopc-learning-android.github.io/git-card/get?user=fcannizzaro&repo=material-tip)

# Sample

![](https://raw.githubusercontent.com/nilopc-learning-android/material-tip/master/preview.gif)

# How to include

**Step 1.** Add it in your root **build.gradle** at the end of repositories:

```gradle
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
  }
```

**Step 2.** Add the dependency


```gradle
dependencies {
    compile 'com.github.fcannizzaro:material-tip:1.0.5'
}
```

# Custom View + TipBehavior

```xml
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    	<android.support.design.widget.FloatingActionButton
       		...
        	app:layout_behavior="com.github.fcannizzaro.materialtip.TipBehavior"/>

    	...

    	<com.github.fcannizzaro.materialtip.MaterialTip
			android:id="@+id/tip"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_gravity="bottom"
			...
			app:tip_background="color"
			app:tip_color="color"
			app:tip_text_color="color"
			app:tip_title_color="color"
			app:tip_icon="drawable"
			app:tip_negative="string"
			app:tip_positive="string"
			app:tip_text="string"
			app:tip_title="string"/>

</android.support.design.widget.CoordinatorLayout>
```

# Builder Methods

### withButtonListener(ButtonListener)
Attach the button listener

```java
new ButtonListener() {

	@Override
	public void onPositive(MaterialTip tip) {
		System.out.println("positive");
    }

    @Override
    public void onNegative(MaterialTip tip) {
		System.out.println("negative");
	}

}
```

### withTitle(String)
Set tip title

### withText(String)
Set tip text

### withPositive(String)
Set tip positive button text

### withNegative(String)
Set tip negative button text

### withIcon(Drawable)
Set tip icon

### withColor(int)
Set tip primary color

### withBackground(int)
Set tip background color

### withTitleColor(int)
Set tip title color

### withTextColor(int)
Set tip text color

# Note
Each builder method is also available with Resources arg (ex. withTextRes, withTitleRes).

# Void Methods

### show()
animate and show the tip

### hide()
animate and hide the tip

### toggle()
animate and show/hide the tip

# Sample
```java

MaterialTip tip = (MaterialTip) findViewById(R.id.tip);

tip
	.withTitle("Ok Google")
	.withText("Something!")
    .withPositive("save")
    .withNegative("discard")
    .withBackground(Color.parseColor("#363636"))
    .withTextColor(Color.parseColor("#f5f5f5"))
    .withTitleColor(Color.WHITE)
    .setButtonListener(new ButtonListener() {

        @Override
        public void onPositive(MaterialTip tip) {
        	System.out.println("positive");
        }

        @Override
        public void onNegative(MaterialTip tip) {
        	System.out.println("negative");
        }

});

tip.show();
```

# License
MIT - Francesco Cannizzaro

