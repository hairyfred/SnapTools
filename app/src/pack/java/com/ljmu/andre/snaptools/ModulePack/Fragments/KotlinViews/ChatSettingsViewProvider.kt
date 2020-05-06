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
import com.ljmu.andre.snaptools.ModulePack.ChatSaving.CustomNotifications

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
                    var typingstring = ("default")
                    var addstring = ("defualt")

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
                                setText("SNAP PLACEHOLDER")
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
                                setText("CHAT PLACEHOLDER")
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
                                setText("TYPING PLACEHOLDER")
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
                                setText("ADD PLACEHOLDER")
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
                        themedButton(ResourceUtils.getStyle(context, "NeutralButton")) {
                            id = "button_apply_Custom_notifications".toId()
                            text = "Apply Custom notifications"
                            visibility = View.GONE

                            setOnClickListener {
                                CustomNotifications snap = (String) snap.replace(snapstring)
                                CustomNotifications chat = chat.replace(chatstring)
                                CustomNotifications typing = typing.replace(typingstring)
                                CustomNotifications add = add.replace(addstring)
                            }
                        }
                    }
                }
            }.view as T
}
