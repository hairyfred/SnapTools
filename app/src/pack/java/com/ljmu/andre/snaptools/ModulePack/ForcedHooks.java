package com.ljmu.andre.snaptools.ModulePack;

import android.content.Context;

import com.ljmu.andre.GsonPreferences.Preferences.Preference;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.Utils.XposedUtils.ST_MethodHook;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookDef.ERROR_SUPPRESS_DOWNLOADER_RUNNABLE;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookDef.NETWORK_EXECUTE_SYNC;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.STORY_BLOCKER_ADVERTS_BLOCKED;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.STORY_BLOCKER_DISCOVER_BLOCKED;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.DISABLED_MODULES;
import static com.ljmu.andre.snaptools.Utils.PreferenceHelpers.collectionContains;
import static de.robv.android.xposed.XposedHelpers.callMethod;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ForcedHooks extends ModuleHelper {

    private boolean miscChangesEnabled;

    public ForcedHooks(String name, boolean canBeDisabled) {
        super(name, canBeDisabled);
    }

    @Override
    public FragmentHelper[] getUIFragments() {
        return new FragmentHelper[0];
    }

    @Override
    public void loadHooks(ClassLoader snapClassLoader, Context snapContext) {
        boolean blockDiscovery = getPref(STORY_BLOCKER_DISCOVER_BLOCKED);
        boolean blockAds = getPref(STORY_BLOCKER_ADVERTS_BLOCKED);

        miscChangesEnabled = !collectionContains(DISABLED_MODULES, "Misc Changes");



        hookMethod(
                NETWORK_EXECUTE_SYNC,
                new ST_MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        String url = (String) callMethod(param.thisObject, "getUrl");

                        Timber.d("Network URL: " + url);

                        if (url.endsWith("logout")) {
                            Timber.d("Blocking logout");
                            param.setResult(null);
                        }
                    }
                }
        );
        // Error Suppression for the hook above
        hookMethod(
                ERROR_SUPPRESS_DOWNLOADER_RUNNABLE,
                new HookWrapper((HookAfter) param -> {
                    if (param.getThrowable() != null) {
                        Timber.d("Download Runnable Error Suppression");
                        param.setThrowable(null);
                    }
                })
        );
    }

    private String transformOtherString(Preference preference) {
        if (!miscChangesEnabled)
            return null;

        String preferenceValue = getPref(preference);

        if (preferenceValue == null || preferenceValue.equals("Default")) {
            return null;
        }

        return preferenceValue;
    }

    private Boolean transformBoolean(Preference preference) {
        if (!miscChangesEnabled)
            return null;

        String preferenceValue = getPref(preference);

        if (preferenceValue != null) {
            if (preferenceValue.equals("On")) {
                return true;
            } else if (preferenceValue.equals("Off")) {
                return false;
            }
        }

        return null;
    }

    private String transformOverwrite(Preference preference) {
        if (!miscChangesEnabled)
            return null;

        String preferenceValue = getPref(preference);

        if (preferenceValue != null) {
            if (preferenceValue.equals("On")) {
                return "FORCE_ENABLED";
            } else if (preferenceValue.equals("Off")) {
                return "FORCE_DISABLED";
            }
        }

        return null;

    }

    private <T> T transformOtherOnOff(Preference preference, T on, T off, T def) {
        if (!miscChangesEnabled)
            return null;

        String preferenceValue = getPref(preference);

        if (preferenceValue != null) {
            if (preferenceValue.equals("On")) {
                return on;
            } else if (preferenceValue.equals("Off")) {
                return off;
            }
        }

        return def;
    }

    private void handleExperiment(MethodHookParam param, Object experimentMode) {
        if (!miscChangesEnabled)
            return;

        if (experimentMode != null)
            param.setResult(experimentMode);
    }

    private void handleABTestPrinting(MethodHookParam param) {
        String groupName = (String) param.args[0];
        String experimentName = (String) param.args[1];

        Object result = param.getResult();

        switch (groupName) {
            case "DISCOVER_V2":
                break;
            default:
                Timber.d("ABTest [Group: %s][Exp: %s][Def: %s][Res: %s]", groupName, experimentName, param.args[2],
                        result + (result != null ? "(" + result.getClass() + ")" : ""));
        }
    }
}
