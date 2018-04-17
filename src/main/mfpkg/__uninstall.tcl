# ============================================================================
# Uninstall Plugins
# ============================================================================
set plugin_label           [string toupper PACKAGE_$package]
set plugin_namespace       /mflux/plugins/unimelb-mf-commons-net-plugin
set plugin_zip             unimelb-mf-commons-net-plugin.zip
set plugin_jar             unimelb-mf-commons-net-plugin.jar
set module_class           unimelb.mf.commons.net.plugin.UnimelbCommonsNetPluginModule

if { [xvalue exists [plugin.module.exists :path ${plugin_namespace}/${plugin_jar} :class ${module_class}]] == "true" } {
    plugin.module.remove :path ${plugin_namespace}/${plugin_jar} :class ${module_class}
}

if { [xvalue exists [asset.namespace.exists :namespace ${plugin_namespace}]] == "true" } {
    asset.namespace.destroy :namespace "${plugin_namespace}"
}

system.service.reload

srefresh
