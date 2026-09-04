#built using mc-build (https://github.com/mc-build/mc-build)

execute store result bossbar minecraft:emissary value run scoreboard players get Emissary em.health
bossbar set minecraft:emissary color red
schedule function emissary:ai/hitbox/__generated__/sequence/1 2t replace
schedule function emissary:ai/hitbox/__generated__/sequence/2 4t replace
schedule function emissary:ai/hitbox/__generated__/sequence/3 6t replace
execute as @e[tag=emissarymodel] at @s run function emissary:ai/hitbox/__generated__/block/743