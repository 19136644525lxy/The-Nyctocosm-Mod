#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard objectives add em.timer dummy
scoreboard objectives add em.phase dummy
scoreboard objectives add em.health dummy
scoreboard objectives add em.value dummy
scoreboard objectives add em.timer dummy
scoreboard objectives add em.cutscenetimer dummy
scoreboard players set EmissaryMaxHealth em.health 1200
scoreboard players set lightningjavelindamage em.value 45
scoreboard players set meteorshowerdamage em.value 40
scoreboard players set wingbeatdamage em.value 5
scoreboard players set pillarsdamage em.value 35
scoreboard players set swordplungedamage em.value 60
scoreboard players set wingblastdamage em.value 40
scoreboard players set celestialsmitedamage em.value 80
scoreboard players set flamegeysersdamage em.value 40
scoreboard players set swordswingdamage em.value 50
scoreboard players set crossdamage em.value 35
scoreboard players set swordspindamage em.value 60
scoreboard players set groundslamdamage em.value 60
scoreboard players set flameslashdamage em.value 50
scoreboard players set seekingmissilesdamage em.value 15
scoreboard players set lightningfielddamage em.value 35
scoreboard players set godraysdamage em.value 40
scoreboard players set apocalypseraindamage em.value 12
scoreboard players set fireballdamage em.value 35
scoreboard players set corkscrewdamage em.value 60
scoreboard players set divineblastdamage em.value 80
scoreboard players set grappledamage em.value 25
scoreboard players set MaxPoise em.value 7
bossbar add emissary {"translate":"entity.nyctocosm.bossbar.vc","color":"gold","with":[{"translate":"entity.nyctocosm.bossbar.emissary","color":"gold"}]}
bossbar set minecraft:emissary max 1200
bossbar set minecraft:emissary color yellow
bossbar set minecraft:emissary style notched_6