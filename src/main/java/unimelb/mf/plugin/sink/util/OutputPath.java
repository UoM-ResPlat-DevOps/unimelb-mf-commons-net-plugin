package unimelb.mf.plugin.sink.util;

import arc.xml.XmlDoc;
import io.github.xtman.util.PathUtils;

public class OutputPath {

    public static String getOutputPath(String baseDir, String assetSpecificOutputPath, String contextualOutputPath,
            XmlDoc.Element assetMeta, boolean unarchive) throws Throwable {

        String ext = assetMeta == null ? null : assetMeta.value("content/type/@ext");
        String assetId = assetMeta == null ? null : assetMeta.value("@id");
        String assetPath = assetMeta == null ? null : assetMeta.value("path");
        String assetName = assetMeta == null ? null : assetMeta.value("name");
        String assetNamespace = assetMeta == null ? null : assetMeta.value("namespace");

        StringBuilder sb = new StringBuilder();
        if (baseDir != null && !baseDir.trim().isEmpty()) {
            sb.append(PathUtils.trimTrailingSlash(PathUtils.normalise(baseDir)));
        }

        if (assetSpecificOutputPath != null) {
            if (sb.length() > 0) {
                sb.append("/");
            }
            sb.append(PathUtils.trimSlash(PathUtils.normalise(assetSpecificOutputPath)));
        } else if (contextualOutputPath != null) {
            // contextualOutputPath is usually asset.namespace if called by
            // asset.get; or path generated by shopping.cart services
            if (sb.length() > 0) {
                sb.append("/");
            }
            contextualOutputPath = PathUtils.trimSlash(PathUtils.normalise(contextualOutputPath));
            sb.append(contextualOutputPath);
            if (assetNamespace != null) {
                if (contextualOutputPath.equals(PathUtils.trimLeadingSlash(assetNamespace))) {
                    if (assetName != null) {
                        sb.append("/").append(assetName);
                    } else {
                        sb.append("/__asset_id__").append(assetId);
                    }
                }
            }
        } else if (assetPath != null) {
            if (sb.length() > 0) {
                sb.append("/");
            }
            sb.append(PathUtils.trimSlash(PathUtils.normalise(assetPath)));
        } else {
            if (sb.length() > 0) {
                sb.append("/");
            }
            if (assetNamespace != null) {
                sb.append(PathUtils.trimLeadingSlash(assetNamespace));
                sb.append("/");
            }
            if (assetName != null) {
                sb.append(assetName.replace('/', '_'));
            } else {
                sb.append("__asset_id__").append(assetId);
            }
        }
        String outputPath = sb.toString();
        if (outputPath.isEmpty()) {
            throw new Exception("Failed to generate output path.");
        }
        if (unarchive) {
            if (ext != null && (outputPath.endsWith("." + ext) || outputPath.endsWith("." + ext.toUpperCase()))) {
                outputPath = outputPath.substring(0, outputPath.length() - ext.length() - 1);
            }
        } else {
            if (ext != null && !(outputPath.endsWith("." + ext) || outputPath.endsWith("." + ext.toUpperCase()))) {
                outputPath = outputPath + "." + ext;
            }
        }
        return outputPath;
    }

}