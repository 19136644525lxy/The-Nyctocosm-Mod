#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s rotated ~ 0 positioned ~ ~1 ~ run function effects:shockwave/transmittance_first
execute store result score TEMP UUID0 run scoreboard players get @s PUUID0
execute store result score TEMP UUID1 run scoreboard players get @s PUUID1
execute store result score TEMP UUID2 run scoreboard players get @s PUUID2
execute store result score TEMP UUID3 run scoreboard players get @s PUUID3
execute as @a[tag=in_plane,distance=..3] at @s run function spells:transmittance/__generated__/block/475