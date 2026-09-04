#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard objectives add s.yTilt dummy
scoreboard objectives add s.damageTaken dummy
scoreboard objectives add s.beamDelay dummy
scoreboard players set SerpentMaxHealth value 1000
bossbar add serpent {"translate":"entity.nyctocosm.bossbar","color":"gold","with":[{"translate":"entity.nyctocosm.bossbar.serpent","color":"dark_red"}]}
bossbar set minecraft:serpent color red
bossbar set minecraft:serpent style notched_12
execute store result bossbar minecraft:serpent max run scoreboard players get SerpentMaxHealth value