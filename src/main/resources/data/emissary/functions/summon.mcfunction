#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ 50 positioned ^ ^ ^-5 run function emissary:__generated__/block/28
scoreboard players set Emissary em.phase 0
scoreboard players operation Emissary em.health = EmissaryMaxHealth em.health
execute store result bossbar minecraft:emissary max run scoreboard players get Emissary em.health
execute store result bossbar minecraft:emissary value run scoreboard players get Emissary em.health
scoreboard players set EmissaryInvulnerable em.value 1