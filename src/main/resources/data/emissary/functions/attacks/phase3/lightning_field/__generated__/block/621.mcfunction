#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[distance=..4,gamemode=!spectator,tag=!emissarydebug] run function magic:calcstatsdamage
execute as @a[distance=..4,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/lightning_field/__generated__/block/622
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @a[distance=..4,gamemode=!spectator,tag=!emissarydebug] run function damage:damage_dissonance/apply
scoreboard players operation @s m.damage_taken.dissonance += damageAmt value
execute as @e[tag=damagetemp,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function emissary:attacks/phase3/lightning_field/__generated__/block/623
scoreboard players reset IsPlayer value