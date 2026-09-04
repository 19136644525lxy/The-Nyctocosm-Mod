#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=!custom,predicate=nyctocosm:mobs/nightmare_apparition/night_wastes_spawn] run function nyctocosm:mobs/nightmare_apparition/spawn
execute as @s[predicate=nyctocosm:mobs/death_walker/night_wastes_spawn] at @s run function nyctocosm:mobs/death_walker/spawn
execute at @s run tp @s ~ -100 ~
data merge entity @s {Health:0.0f,DeathTime:19s,DeathLootTable:"minecraft:empty"}