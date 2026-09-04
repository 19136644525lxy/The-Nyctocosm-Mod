#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #4 const
scoreboard players operation 0 temp += Submergence s.damage
scoreboard players operation TEMP s.damage = 0 temp
scoreboard players operation 0 temp = TEMP s.damage
scoreboard players operation 0 temp *= #1000 const
scoreboard players operation TEMP s.damage = 0 temp
execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
execute as @e[type=#nyctocosm:livingthings,tag=!s.immoveable,tag=!dais,nbt={OnGround:1b},distance=3..7,limit=6,sort=nearest] at @s run function spells:submergence/__generated__/block/403
scoreboard players reset TEMP s.damage
playsound nyctocosm:spells.transmutate.submergence master @a[distance=..50] ~ ~ ~ 10