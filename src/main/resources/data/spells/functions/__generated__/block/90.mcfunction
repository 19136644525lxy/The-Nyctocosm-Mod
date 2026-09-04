#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
execute as @a run function spells:__generated__/block/91
execute if score @s spelltimer matches ..0 run function spells:__generated__/block/93
tp @a[tag=matchedid,predicate=!nyctocosm:is_sneaking] ~ ~ ~
tag @a remove matchedid
execute at @s positioned ~ ~1 ~ run function effects:spells/special/adamance
execute at @s positioned ~ ~2 ~ run function effects:spells/special/adamance
execute at @s positioned ~ ~ ~ run function effects:spells/special/adamance