package io.github.yukkuritaku.lmjsondata;

import java.util.Map;

public class LmmlJarUrlMap {

    private static final Map<String, Map<String, JarMetadata>> LMML_JAR_URL_MAP =
            Map.ofEntries(
                    e("1.16", Map.of(
                            "2.0.0", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACwcMYvxtpps7siCbeBIkdoa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.0.jar"),
                            "2.0.1", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACU5boj9lmYGL5FXUv1s3Tya/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.1.jar"),
                            "2.0.2", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAChTwbcHoLUoyAoPAmLtBzga/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.2.jar"),
                            "2.0.3", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AABNErb8z343Z8051O9PpBjga/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.3.jar"),
                            "2.0.4", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAALxRywikzi_H7NFbLTmMBha/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.4.jar"),
                            "2.0.5", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB9dyD397xi2cYUY6PvRLp3a/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.5.jar"),
                            "2.0.6", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACGR-AKSaoxedoEHSZbm_CJa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.6.jar"),
                            "2.0.7", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAvWwYmAVatsq-HNb00VrFTa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.7.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABakgArcySze7EE5vmxFgwZa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16-2.0.7-dev.jar")
                    )),
                    e("1.16.3", Map.of("1.0.0",
                            JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACYkNYYbbfAnNb0tQVFovzka/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.3-1.0.0.jar",
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABICNqJn1C3bS2F_lFmMIBIa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.3-1.0.0-dev.jar"))),
                    e("1.16.4", Map.ofEntries(
                            e("1.0.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABr5WCq7DsUxODWOg9b_Xcga/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.1.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAD7zo2L_BnZtSXJCmfu_dAva/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.1-dev.jar")),
                            e("1.0.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAmDbCOBXjTvc-h0osArUI5a/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.2.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACXVsiVAiHC6gMVywvuK4mwa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.2-dev.jar")),
                            e("1.0.3", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADtVXCk3m9ZcoCqT6tahuLta/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.3.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADqDl_LXpbOKKavCMP2rDLja/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.3-dev.jar")
                                    ),
                            e("1.0.4", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACtFE0gyLrSMt3uEuexezira/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.4.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABmpDgLxng18rk3_5uDkjcBa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.4-dev.jar")
                                    ),
                            e("1.0.5", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADBO15hVXvv1Fjj-CHUc35Ca/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.5.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACCFy0a3WmWwSnnDuKPLz1qa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.5-dev.jar")
                                    ),
                            e("1.0.6", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACL9-aoeuYQF0cKVDsVeD1ya/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.6.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADgYH0CQeVQRQM5hdln33_5a/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.6-dev.jar")
                                    ),
                            e("1.0.7", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADNG77KrC-IRGDfMMbrLXwHa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.7.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAMq5w9ep2ClV4xn2ZLbRISa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.7-dev.jar")
                                    ),
                            e("1.0.8", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAD54ZUdnSRKvd2mYoFyLNaaa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.8.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACr1fAUWai-GrKKLzqnHvm0a/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.8-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADK3tgH_PShBQMRCVoa6DHqa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.8-sources.jar")
                                    ),
                            e("1.0.9", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AADgxYu4TNED-IBnLs8ZmWMza/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.9.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADkH_c1qGrAp2pBCvMeZIvwa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.9-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB0ghaQZNBvS70gMJ_IidW-a/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.9-sources.jar")
                                    ),
                            e("1.0.10", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAD4ZPQI0Ltqx_qmg15NCvYja/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.10.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAbr-E2aI5DHA4xAzNnZ_Aia/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.10-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABKKIOfcJQRxzg4P_rSlSjMa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.10-sources.jar")
                                    ),
                            e("1.0.11", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA0Molfdvct9vMmJYI9wRSAa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.11.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABilPGhBcQFA7wAeOxanWSOa/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.11-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABIhxcL0fzI3MiuO9_tSZgza/LittleMaidModelLoader/old/LMML-Fabric-1.16.x/LMML-Fabric-1.16.4-1.0.11-sources-dev.jar")
                            ))),
                    e("1.16.5",
                            Map.of(
                                    "2.0.8", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADeFpIsn0PKSFiMb8KIspxla/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.0.8.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAejObp1S-a_GI5eWfv-Xuka/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.0.8-dev.jar"),
                                    "2.0.9", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAD0yDcdyXWRWrNhKK0Eaws3a/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.0.9.jar"),
                                    "2.1.0", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAdc-XjbyBOnnTV5_o9AHiHa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.1.0.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABDywozXnznzbEN3-2WqdT0a/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.1.0-dev.jar"),
                                    "2.1.1", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB2eiLezdeUI3jrkHz-9qAfa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.1.1.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABtZTuvHRRuNKLUJDiYmyhla/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.1.1-dev-shadow.jar"),
                                    "2.2.0", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAWdtkfLM0cKWHPJ10BZG_ka/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.2.0.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADyNf44zbmszOuaSvLvXo5Ja/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-Fa-Arc-1.16.5-2.2.0-dev-shadow.jar"),
                                    "2.3.0", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAAHB2yXO9zwCj0dpgdt7N6a/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.3.0-Fabric.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAC78VeKydRLdqZulTlEortPa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.3.0-fabric-dev.jar"),
                                    "2.4.0", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADEG5Gq2BVhTXTYNe00G62qa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.4.0-Fabric.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACMuQB0zxhrFfyfQdB58nYEa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.4.0-fabric-dev.jar"),
                                    "2.5.0", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABMLvFLHl8M6BMAFlJ-tvLOa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.5.0-Fabric.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABTEDW4Bw4bI4-I5WKIgpdSa/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.5.0-fabric-dev.jar"),
                                    "2.5.1", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADATxo66mqd6Ma7Kxvkm9-ja/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.5.1-Fabric.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADIlc6MCgFvgfznpUbnsseva/LittleMaidModelLoader/Fabric/1.16.x/old/LMML-1.16.5-2.5.1-fabric-dev.jar"),
                                    "2.5.3", JarMetadata.create(
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAKa0dMZFPCICnUwpvdkXZQa/LittleMaidModelLoader/Fabric/1.16.x/LMML-1.16.5-2.5.3-Fabric.jar",
                                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADn9wtprXekRaz0oxt0qndGa/LittleMaidModelLoader/Fabric/1.16.x/LMML-1.16.5-2.5.3-fabric-dev.jar"))

                    ),
                    e("1.17", Map.of(
                            "3.0.0", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACcF-kvu_MswI1TY60ptoasa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17-3.0.0.jar"),
                            "3.0.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABr5c49eYGGX-xGxxOE3kOda/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17-3.0.1.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACayWtfUZjJpX3YHbPiRiy4a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17-3.0.1_Dev.jar"),
                            "3.0.3", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB0H9paXM80TveBYZq7vitva/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17-3.0.3.jar")
                    )),
                    e("1.17.1", Map.ofEntries(
                            e("3.0.4", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACYLGIxXAu4NtogHn9VXSyIa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.4.jar")),
                            e("3.0.5", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAANxtR2Y1oU9Us_FzfVdN_-a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.5.jar")),
                            e("3.0.6", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AABKUfqNwUIZoAYy-RTjkVWoa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.6.jar")),
                            e("3.0.7", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AABnql-8C6vbA3DyTrCGHKzVa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.7.jar")),
                            e("3.0.8", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACL8xx0Nb1kcXuFanWYI93Pa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.8.jar")),
                            e("3.0.9", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AACZ1_fXRXMoyschq6Sq8NFea/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.9.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABEAsjYWxGpMCPFe3kLL-1Ka/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.9-dev.jar")),
                            e("3.0.10", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADS9k-AW9v31lxhbJOBHRY0a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.10.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB0VCwju9rutcnVciPiCX1Ta/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.10-dev.jar")
                                    ),
                            e("3.0.11", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AABk6u4L73aEPbjjLViOWl89a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.0.11.jar")
                                    ),
                            e("3.1.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABUyoGvcXIwtZm3qkYn8EfQa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.1.0.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADabVlHJQXgVlMIeXy0Ah9ga/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.1.0-dev.jar")
                                    ),
                            e("3.1.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAASp4ItjsXArL8g63WsGv4wa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.1.1.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADmdX3lrFhlC7KUP1B162SMa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.1.1-dev-shadow.jar")
                                    ),
                            e("3.2.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAX5HZUlcD6HtbwEbqux-KTa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.2.0.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACu-aUZoBIgJweDc2b5R6Vfa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-Fa-Arc-1.17.1-3.2.0-dev-shadow.jar")
                                    ),
                            e("3.3.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADZ3KBxxxrwHdwpTLxxMSaEa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.3.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADFhkLpCMv6C7QYnIAYIBt1a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.3.0-fabric-dev.jar")
                                    ),
                            e("3.4.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADUxHII74P3rFgs7Di4ZjM5a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.4.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADN4NNYdkzVoceP7maQToYja/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.4.0-fabric-dev.jar")
                                    ),
                            e("3.4.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACd18d6Ho8xZZ5dRPRUzzafa/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.4.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADlN7OuyR-vH2dBiBWb8KE6a/LittleMaidModelLoader/Fabric/1.17.x/old/LMML-1.17.1-3.4.1-fabric-dev.jar")
                            //なぜか3.4.2リリース版の方だけMETA-INFしか入ってない
                            ),
                            e("3.4.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADH_4qexMJiKvbUzB3UieEVa/LittleMaidModelLoader/Fabric/1.17.x/LMML-1.17.1-3.4.2-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADBkpUvPC9KVZ-WmqUbvbgta/LittleMaidModelLoader/Fabric/1.17.x/LMML-1.17.1-3.4.2-fabric-dev.jar"))
                    )),
                    e("1.18", Map.of(
                            "4.0.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAABgL0N_Ty1Q0yKGTDCB3TPa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18-4.0.0.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACv-BVPS2NvRgKr28XAzKvsa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18-4.0.0-dev.jar"
                            ),
                            "4.0.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADAiW3L-uf3J0HpaJ3fYFDla/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18-4.0.1.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA0PaIGQX3bc141u57gsRbGa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18-4.0.1-dev-shadow.jar")
                    )),
                    e("1.18.1", Map.of(
                            "4.0.3", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAD-qkELtqG5V_2q6kGFruUPa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18.1-4.0.3.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAC2hcsToMZP0RO8ETIxp6nPa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18.1-4.0.3-dev-shadow.jar"),
                            "4.1.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABCP8zrp_iX5pVg-BtXqykGa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18.1-4.1.0.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABtKElNjWo0frGrk_sUt6dIa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-Fa-Arc-1.18.1-4.1.0-dev-shadow.jar")
                    )),
                    e("1.18.2", Map.ofEntries(
                            e("4.2.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACao4TMVklPfl-TVHR-FUDGa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.2.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACbKcjME8hae7UcqeAw_KTBa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.2.0-fabric-dev.jar")),
                            e("4.3.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACOWN5Fwrr4TMlPgp2mU56ya/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.3.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAApN_HEcUeg1qnlx92U3Onpa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.3.0-fabric-dev.jar")),
                            e("4.4.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAQWAj9GAU8SkL-EnGqLREea/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAWdG2r7dJ_rifnqFfDdBYNa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.0-fabric-dev.jar")),
                            e("4.4.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACe9AB09wDgbiAzcU4fr9WKa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACzxrKZTaD2wITlpyaV3lfTa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.1-fabric-dev.jar")),
                            e("4.4.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAG4SWrsgEOCy5ybi96Lan-a/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.2-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADmbgyao29azw3wibB83BXBa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.2-fabric-dev.jar")
                                    ),
                            e("4.4.3", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAeGAyd_tdft0w2IOC4rjlDa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.3-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADd17T2djSD0UEBq1OOanxWa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.3-fabric-dev.jar")
                                    ),
                            e("4.4.5", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABdXUF0oc-0fKpgdJUo8ZQba/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.5-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAC-P1GO-5mdBmM11lEipryCa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.5-fabric-dev.jar")
                                    ),
                            e("4.4.6", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAC9TUuwJTqPT7z7ZkhgMp67a/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.6-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADziTU_SIRqI1oo5e-mL_ELa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.4.6-fabric-dev.jar")
                                    ),
                            e("4.5.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADRP-_m5bcCsaSHEQ-_7AvXa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.5.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABGAi5CP6tURSj5Py4-efPFa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.5.0-fabric-dev.jar")
                                    ),
                            e("4.6.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADHdx2Qe3DBxHaBhxuIrCHGa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACQhUKgOaJ5XhEUD0BQQltoa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.0-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAC9wnrRAH8wRXSBUL1C9tPia/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.0-sources.jar")
                                    ),
                            e("4.6.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB5lyT1b0Jran300R_Wt5xZa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAfOBLHjXJdjtpyUm6ipJ3fa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.1-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACwmQTJy9ayOkm0jAE4WRbTa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.1-sources.jar")
                                    ),
                            e("4.6.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAALJKmrAFeb1YH7lZBGnGlya/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.2-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABJL7_XUJeqw-GqL_auH6xda/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.2-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABIXHUPcyUXVpYp-7UdQuWwa/LittleMaidModelLoader/Fabric/1.18.x/old/LMML-1.18.2-4.6.2-sources.jar")
                                    ),
                            e("4.6.4", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAFen2Ol-8sEhk8nEyfqlura/LittleMaidModelLoader/Fabric/1.18.x/LMML-1.18.2-4.6.4-Fabric.jar")))),
                    e("1.19", Map.of(
                            "5.0.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABlRhGfaBP44gEx1czA_bbla/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACdKKiEJtZvEgXEmT7h77Z-a/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.0-fabric-dev.jar"),
                            "5.0.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA_Ds5YKaywxjQc_6frWi_qa/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABaTaMrBIVve-sJ7u1LyAz4a/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.1-fabric-dev.jar"),
                            "5.0.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABrk8TWKjZM4Rp9p8YdaCx3a/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.2-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACSlWrX2DyUXf6IHq1TqU7Pa/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.2-fabric-dev.jar"),
                            "5.0.3", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAB7JG_3FsPdQYSepAQHjd4ja/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.3-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADtag_AiLABtOv2f9_9H9TBa/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19-5.0.3-fabric-dev.jar")
                    )),
                    e("1.19.2", Map.of(
                            "5.1.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADsKeNxKx_LdBDi1Zdav3MIa/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.1.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACi8BdgyUmKJsvgiZ_cYt5za/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.1.0-fabric-dev.jar"),
                            "5.1.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADsHJ83lS4gGaXEyPgG_IBla/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.1.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABaMspX0yvT-BJ2HgoVHsg4a/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.1.1-fabric-dev.jar"),
                            "5.2.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA_AeFUA1dxwJzirFVRnPDfa/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABZBgdI4hAOnh7buuoB5Vcra/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.0-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADcGk1G8-qMAFWMOy9q_tSga/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.0-sources.jar"),
                            "5.2.1", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AADyFVH20bjhqLC1E5Y1Ls-ca/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAWjQtUaEOgLjzAYbJ-0Upga/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.1-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADNa19y7MG9VZqnHoBAPo2Ja/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.1-sources.jar"),
                            "5.2.2", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADFacdMntD4vOPqrz4npgYla/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.2-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACDWb7rOs6rUk057nduaS_la/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.2-fabric-dev.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA--sQQxx3CWd4ex-j-2S23a/LittleMaidModelLoader/Fabric/1.19.2/old/LMML-1.19.2-5.2.2-sources.jar"),
                            "5.2.3", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AAChwEiTvinWk9wyejgZpFSNa/LittleMaidModelLoader/Fabric/1.19.2/LMML-1.19.2-5.2.3-Fabric.jar")
                    )),
                    e("1.19.3", Map.of(
                            "6.0.0", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABxVMySS1VdskRCujbwQP63a/LittleMaidModelLoader/Fabric/1.19.3/old/LMML-1.19.3-6.0.0-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABTxaat1gjDK-MgwQ9F3dY9a/LittleMaidModelLoader/Fabric/1.19.3/old/LMML-1.19.3-6.0.0-fabric-dev.jar"),
                            "6.0.1", JarMetadata.create(
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACafhO6Q_r_1p9_2A_pBE7ra/LittleMaidModelLoader/Fabric/1.19.3/old/LMML-1.19.3-6.0.1-Fabric.jar",
                                    "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAA0tIMkibAiy5Mk00tR7uWya/LittleMaidModelLoader/Fabric/1.19.3/old/LMML-1.19.3-6.0.1-fabric-dev.jar"),
                            "6.0.2", JarMetadata.create(
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADTTZblojACvCmC3zkmMRNoa/LittleMaidModelLoader/Fabric/1.19.3/LMML-1.19.3-6.0.2-Fabric.jar",
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACoR9rvV5_V9cHYhmfVnORMa/LittleMaidModelLoader/Fabric/1.19.3/LMML-1.19.3-6.0.2-fabric-dev.jar"))),
                    e("1.19.4", Map.of("7.0.1", JarMetadata.create(
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACfN2ElnIk5E0Q6utFlXalsa/LittleMaidModelLoader/Fabric/1.19.4/LMML-1.19.4-7.0.1-Fabric.jar",
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADVWJMUUvaawUdHvo9LEw73a/LittleMaidModelLoader/Fabric/1.19.4/LMML-1.19.4-7.0.1-fabric-dev.jar",
                            "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAui1mMt7oD81aFSYTvLSdXa/LittleMaidModelLoader/Fabric/1.19.4/LMML-1.19.4-7.0.1-sources.jar"))),
                    e("1.20", Map.of("8.0.0", JarMetadata.create("https://www.dropbox.com/sh/tzkdz46y67tuohx/AABd4Q-tbT3q6EXTUhCpWfE0a/LittleMaidModelLoader/Fabric/1.20/LMML-1.20.1-8.0.0-Fabric.jar")))
            );


    private static <K, V> Map.Entry<K, V> e(K key, V value) {
        return Map.entry(key, value);
    }

    public static Map<String, Map<String, JarMetadata>> getLmmlJarUrlMap() {
        return LMML_JAR_URL_MAP;
    }
}
