#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #50 const
scoreboard players add 0 temp 100
scoreboard players operation TRANGE value = 0 temp
execute if score TRANGE value matches 300.. run scoreboard players set RANGE value 300
execute at @s run playsound nyctocosm:spells.transmutate.transmittance master @a[distance=..50] ~ ~ ~ 10
execute at @s run function spells:transmittance/__generated__/block/477
effect give @s levitation 2 255 true
effect give @s slowness 1 4 true
execute at @s positioned ~ ~1.625 ~ run function effects:elements/void/transmutate/play
execute at @s positioned ~ ~1.625 ~ run function spells:transmittance/raycast
stopsound @s master nyctocosm:spells.hum.magic_plane