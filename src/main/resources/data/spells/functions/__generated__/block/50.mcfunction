#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s UUID0 run scoreboard players get @s PUUID0
execute store result score @s UUID1 run scoreboard players get @s PUUID1
execute store result score @s UUID2 run scoreboard players get @s PUUID2
execute store result score @s UUID3 run scoreboard players get @s PUUID3
execute if score @s UUID0 = UUID0 value if score @s UUID1 = UUID1 value if score @s UUID2 = UUID2 value if score @s UUID3 = UUID3 value run function spells:__generated__/block/51