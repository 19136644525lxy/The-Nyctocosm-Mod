#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function sorcerer:attacks/frozenblades/__generated__/block/97
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:damage_void/apply
scoreboard players operation @s m.damage_taken.void += damageAmt value
execute as @e[tag=sorcerer,tag=sorcerermodel,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function sorcerer:attacks/frozenblades/__generated__/block/98
scoreboard players reset IsPlayer value