#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function serpent:__generated__/block/11
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:damage_generic/apply
scoreboard players operation @s m.damage_taken.generic += damageAmt value
execute as @e[tag=damagetemp,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function serpent:__generated__/block/12
scoreboard players reset IsPlayer value