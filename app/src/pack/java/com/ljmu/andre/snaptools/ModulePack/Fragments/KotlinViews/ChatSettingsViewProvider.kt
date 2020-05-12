package com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.EditText
import com.ljmu.andre.GsonPreferences.Preferences
import com.ljmu.andre.GsonPreferences.Preferences.getPref
import com.ljmu.andre.GsonPreferences.Preferences.putPref
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.header
import com.ljmu.andre.snaptools.ModulePack.Fragments.KotlinViews.CustomViews.Companion.label
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toDp
import com.ljmu.andre.snaptools.ModulePack.Utils.KotlinUtils.Companion.toId
import com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.*
import com.ljmu.andre.snaptools.ModulePack.Utils.ViewFactory
import com.ljmu.andre.snaptools.Utils.PreferenceHelpers.putAndKill
import com.ljmu.andre.snaptools.Utils.ResourceUtils
import com.ljmu.andre.snaptools.Utils.themedSwitchCompatX
import org.jetbrains.anko.*

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class ChatSettingsViewProvider {
    fun putPrefSafe(key: Preferences.Preference, value: String) {
        if (value.isEmpty()) {
            putPref(key, null)
        } else {
            putPref(key, value)
        }
    }


    @SuppressLint("ResourceType")
    fun <T : ViewGroup> getMainContainer(activity: Activity): T =
            activity.UI {
                scrollView {
                    lparams(matchParent, matchParent)

                    verticalLayout {
                        header("Chat Saving Settings")

                        themedSwitchCompatX(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Auto save messages in app"
                            isChecked = getPref(SAVE_CHAT_IN_SC)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(SAVE_CHAT_IN_SC, isChecked, activity)
                            }
                        }

                        themedSwitchCompatX(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
                            verticalPadding = 5.toDp()
                            horizontalPadding = 10.toDp()
                            text = "Store messages locally"
                            isChecked = getPref(STORE_CHAT_MESSAGES)

                            setOnCheckedChangeListener { _, isChecked ->
                                putAndKill(STORE_CHAT_MESSAGES, isChecked, activity)
                            }
                        }

                        header("Chat Notifications")

                        themedSwitchCompatX(ResourceUtils.getStyle(activity, "DefaultSwitch")) {
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
                        val types = mutableMapOf(
                                "SNAP" to "Snap",
                                "CHAT" to "Chat",
                                "TYPING" to "Typing",
                                "CHAT_SCREENSHOT" to "Chat Screenshot",
                                "ADD" to "Add",
                                "SCREENSHOT" to "Snap Screenshot",
                                "REPLAY" to "Replay",
                                "SAVE_CAMERA_ROLL" to "Saved Chat Image",
                                "ADDFRIEND" to "Added Back",
                                "INITIATE_AUDIO" to "Call",
                                "INITIATE_VIDEO" to "Video"
                                // Not implemented since "broken": ABANDON_AUDIO, ABANDON_VIDEO, STATUS_BAR
                        )
                        fun ViewManager.addTextBox(title: String, typeName: String): EditText {
                            var textBox: EditText? = null
                            linearLayout {
                                label(title).lparams(width = matchParent, weight = 2f) {
                                    gravity = Gravity.CENTER_VERTICAL
                                }

                                textBox = themedEditText {
                                    setTextAppearance(context, ResourceUtils.getStyle(context, "DefaultText"))
                                    setText(getPref<HashMap<String, String>>(CUSTOM_NOTIFICATION_TEXTS)[typeName])
                                    setSingleLine()
                                    textSize = 16f
                                    leftPadding = 10.toDp()
                                    gravity = Gravity.CENTER_VERTICAL

                                    addTextChangedListener(object : ViewFactory.EditTextListener() {
                                        override fun textChanged(source: EditText?, editable: Editable?) {
                                            activity.find<Button>("button_apply_Custom_notifications".toId()).isEnabled = true
                                        }
                                    })
                                }.lparams(width = matchParent, weight = 1f)
                            }
                            return textBox ?: throw IllegalStateException("Could not initialize TextBox")
                        }

                        val boxes = types.map { (k, v) ->
                            k to addTextBox(v, k)
                        }

                        themedButton(ResourceUtils.getStyle(context, "NeutralButton")) {
                            id = "button_apply_Custom_notifications".toId()
                            text = "Apply Custom notifications"
                            isEnabled = false

                            setOnClickListener {
                                putAndKill(CUSTOM_NOTIFICATION_TEXTS, boxes.associate { (k, box) ->
                                    val text = box.editableText.toString()
                                    k to (if (text.isEmpty()) null else text)
                                }, activity)
                            }
                        }
                    }
                }
            }.view as T
}

