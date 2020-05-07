package com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ljmu.andre.GsonPreferences.Preferences
import com.ljmu.andre.GsonPreferences.Preferences.getPref
import com.ljmu.andre.GsonPreferences.Preferences.putPref
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.header
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.headerNoUnderline
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.label
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toDp
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toId
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.*
import com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory
import com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill
import com.ljmu.andre.snaptools.Utils.ResourceUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedSwitchCompat
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_ADD
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_CHAT
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_CHATSS
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_SNAP
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_REPLAY
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_SCREENSHOT
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_ADDBACK
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_CALL
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_VIDEO
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_ABANDONVIDEO
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_ABANDONCALL
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.NOTIFICATION_TEXT_CROLL

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class ChatSettingsViewProvider {

    @SuppressLint("ResourceType")
    fun <T : ViewGroup> getMainContainer(activity: Activity): T =
            activity.UI {
                scrollView {
                    lparams(matchParent, matchParent)
                    var snapstring =  ("default")
                    var chatstring = ("default")
                    var chatsstring = ("default")
                    var typingstring = ("default")
                    var addstring = ("default")
                    var callstring = ("default")
                    var videostring = ("default")
                    var abandoncallstring = ("default")
                    var abandonvideostring = ("default")
                    var addbackstring = ("default")
                    var crollstring = ("default")
                    var replaystring = ("default")
                    var screenshotstring = ("default")

                    verticalLayout {
                        header("Chat Saving Settings")

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Auto save messages in app"
                            isChecked = getPref(SAVE_CHAT_IN_SC)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(SAVE_CHAT_IN_SC, isChecked, activity)
                            }
                        }

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Store messages locally"
                            isChecked = getPref(STORE_CHAT_MESSAGES)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(STORE_CHAT_MESSAGES, isChecked, activity)
                            }
                        }

                        header("Chat Notifications")

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Disable inbound 'X is typing' notifications"
                            isChecked = getPref(BLOCK_TYPING_NOTIFICATIONS)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(BLOCK_TYPING_NOTIFICATIONS, isChecked, activity)
                            }
                        }

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            id = "switch_stealth_typing".toId()

                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Hide '... is typing' Notification"
                            isChecked = Preferences.getPref(BLOCK_OUTGOING_TYPING_NOTIFICATION)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(BLOCK_OUTGOING_TYPING_NOTIFICATION, isChecked, activity)
                            }
                        }

                        header("Custom Notification Settings")

                        themedSwitchCompat(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Custom notifications"
                            isChecked = getPref(CHANGE_TYPING_NOTIFICATIONS)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(CHANGE_TYPING_NOTIFICATIONS, isChecked, activity)
                            }
                        }

                        linearLayout {
                            label("Snap").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_SNAP))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        snapstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }

                        linearLayout {
                            label("Chat").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_CHAT))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        chatstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Typing").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_TYPING))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        typingstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Add").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_ADD))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        addstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Added Back").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_ADDBACK))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        addbackstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }

                        linearLayout {
                            label("Chat Screenshot").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_CHATSS))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        chatsstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Snap Screenshot").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_SCREENSHOT))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        screenshotstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Saved Chat Image").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_CROLL))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        crollstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }

                        linearLayout {
                            label("Replay").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_REPLAY))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        replaystring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Call").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_CALL))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        callstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Missed Call").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_ABANDONCALL))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        abandoncallstring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Video").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_VIDEO))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        videostring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        linearLayout {
                            label("Missed Video").lparams(width = matchParent, weight = 2f) {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            themedEditText {
                                setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                setText(getPref<String>(NOTIFICATION_TEXT_ABANDONVIDEO))
                                setSingleLine()
                                textSize = 16f
                                leftPadding = 10.toDp()
                                gravity = Gravity.CENTER_VERTICAL

                                addTextChangedListener(object : ViewFactory.EditTextListener() {
                                    override fun textChanged(source: EditText?, editable: Editable?) {
                                        abandonvideostring = editable.toString()
                                        activity.find<Button>("button_apply_Custom_notifications".toId()).visibility = View.VISIBLE
                                    }
                                })
                            }.lparams(width = matchParent, weight = 1f)
                        }
                        themedButton(ResourceUtils.getStyle(context, "NeutralButton")) {
                            id = "button_apply_Custom_notifications".toId()
                            text = "Apply Custom notifications"
                            visibility = View.GONE

                            setOnClickListener {

                                putPref(NOTIFICATION_TEXT_SNAP, snapstring)
                                putPref(NOTIFICATION_TEXT_CHAT, chatstring)
                                putPref(NOTIFICATION_TEXT_TYPING, typingstring)
                                putPref(NOTIFICATION_TEXT_ADD, addstring)
                                putPref(NOTIFICATION_TEXT_CHATSS, chatsstring)
                                putPref(NOTIFICATION_TEXT_CROLL, crollstring)
                                putPref(NOTIFICATION_TEXT_VIDEO, videostring)
                                putPref(NOTIFICATION_TEXT_CALL, callstring)
                                putPref(NOTIFICATION_TEXT_REPLAY, replaystring)
                                putPref(NOTIFICATION_TEXT_ADDBACK, addbackstring)
                                putPref(NOTIFICATION_TEXT_SCREENSHOT, screenshotstring )
                                putPref(NOTIFICATION_TEXT_ABANDONCALL, abandoncallstring )
                                putPref(NOTIFICATION_TEXT_ABANDONVIDEO, abandonvideostring )

                            }
                        }
                    }
                }
            }.view as T
}

