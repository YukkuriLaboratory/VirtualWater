val archives_base_name: String by rootProject
val loader_version: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
}

architectury {
    common("fabric", "forge")
}

loom {
    log4jConfigs.from(file("log4j2.xml"))
}
