#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ^ ^-1 ^6 run tp @e[tag=emissary,tag=attack,tag=grapplepoint] ~ ~ ~
execute as @e[tag=grapplepoint,tag=emissary,tag=attack] at @s unless block ~ ~ ~ #nyctocosm:passthrough on passengers run function emissary:ai/phase/4/__generated__/block/779