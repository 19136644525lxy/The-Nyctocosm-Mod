#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add RANGE em.value 1
execute if score RANGE em.value matches 150.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase2/celestial_smite/__generated__/block/392
execute if entity @a[distance=..5] run function emissary:attacks/phase2/celestial_smite/__generated__/block/393
execute as @s[tag=!endraycast] positioned ^ ^ ^1 run function emissary:attacks/phase2/celestial_smite/__generated__/block/394
execute as @s[tag=endraycast] run function emissary:attacks/phase2/celestial_smite/__generated__/block/395