#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s UUID0 run data get entity @s UUID[0]
execute store result score @s UUID1 run data get entity @s UUID[1]
execute store result score @s UUID2 run data get entity @s UUID[2]
execute store result score @s UUID3 run data get entity @s UUID[3]
execute if score @s UUID0 = TEMP UUID0 if score @s UUID1 = TEMP UUID1 if score @s UUID2 = TEMP UUID2 if score @s UUID3 = TEMP UUID3 run function spells:transmittance/__generated__/block/476