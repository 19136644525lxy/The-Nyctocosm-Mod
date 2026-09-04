#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[type=!#ncdamage:ignore,tag=ncd.hurttimewillend] run function ncdamage:internals/track_life/reset_damage
execute as @s[type=player,scores={ncd.deaths=1..,ncd.health=1..}] run function ncdamage:internals/track_life/reset_damage_after_respawn
execute as @s[type=!#ncdamage:ignore,nbt={HurtTime:2s}] unless score @s ncd.tstlhu matches 8.. run tag @s add ncd.hurttimewillend
tag @s[type=player,scores={ncd.tstlhu=8}] add ncd.hurttimewillend
execute as @s[type=player,tag=ncd.shouldremoveattributes,scores={ncd.tstlhu=1..}] run function ncdamage:internals/remove_attributes
scoreboard players add @a ncd.tstlhu 1
execute as @e[type=!#ncdamage:ignore] run function ncdamage:internals/track_life/track_hp
scoreboard players reset @s ncd_damage
execute store result score #currenthealth ncd.temp run data get entity @s Health 1000
execute if entity @s[type=player,scores={ncd.tstlhu=1..}] if score #currenthealth ncd.temp matches 1001.. run scoreboard players reset @s ncd.virtualhp
execute unless score @s ncd.virtualhp matches -2147483648.. run scoreboard players operation @s ncd.virtualhp = #currenthealth ncd.temp
execute unless score @s ncd_sethealth matches -2147483648.. run scoreboard players operation @s ncd_sethealth = @s ncd.virtualhp
scoreboard players operation @s ncd_sethealth -= @s ncd_damage
scoreboard players operation @s ncd_sethealth += @s ncd_heal
scoreboard players operation @s ncd_sethealth > #0 ncd.num
scoreboard players operation @s ncd_finalchange = @s ncd_sethealth
scoreboard players operation @s ncd_finalchange -= @s ncd.virtualhp
scoreboard players operation @s ncd.currentdmg += @s ncd_damage
execute if score @s ncd_sethealth matches ..0 run kill @s
execute if entity @s[type=!player] unless score @s ncd_finalchange matches 0 run function ncdamage:internals/apply_changes/mob
execute if entity @s[type=player] unless score @s ncd_finalchange matches 0 run function ncdamage:internals/apply_changes/player
scoreboard players reset @s[type=!player] ncd.virtualhp
scoreboard players reset @s ncd_damagetype
scoreboard players reset @s ncd_bypshurttime
scoreboard players reset @s ncd_sethealth
scoreboard players reset @s ncd_damage
scoreboard players reset @s ncd_heal
scoreboard players reset @s ncd_piercing
scoreboard players reset @s ncd_apiercing
scoreboard players reset @s ncd_epiercing