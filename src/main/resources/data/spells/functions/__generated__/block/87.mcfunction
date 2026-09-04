#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
execute as @a[tag=s.submerging] run function spells:__generated__/block/88
scoreboard players add @s spelltimer 1
execute positioned ~ ~5 ~ run particle reverse_portal ~ ~ ~ 0.1 0.1 0.1 0.1 10 force
execute as @s[scores={spelltimer=200..}] run tag @a[tag=matchedid] remove s.submerging
kill @s[scores={spelltimer=200..}]
tag @a remove matchedid