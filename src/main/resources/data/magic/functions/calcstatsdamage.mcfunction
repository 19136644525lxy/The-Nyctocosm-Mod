#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s m.global.resistance.base matches ..0 unless score @s m.global.resistance.base matches 0.. run scoreboard players set @s m.global.resistance.base 0
execute unless score @s m.global.weakness.base matches ..0 unless score @s m.global.weakness.base matches 0.. run scoreboard players set @s m.global.weakness.base 0
scoreboard players operation 0 temp = @s m.global.resistance.base
scoreboard players operation 0 temp += @s m.global.resistance.modifier
scoreboard players operation @s m.global.resistance = 0 temp
scoreboard players operation 0 temp = @s m.global.weakness.base
scoreboard players operation 0 temp += @s m.global.weakness.modifier
scoreboard players operation @s m.global.weakness = 0 temp
execute unless score @s m.dream.resistance.base matches ..0 unless score @s m.dream.resistance.base matches 0.. run scoreboard players set @s m.dream.resistance.base 0
execute unless score @s m.dream.weakness.base matches ..0 unless score @s m.dream.weakness.base matches 0.. run scoreboard players set @s m.dream.weakness.base 0
scoreboard players operation 0 temp = @s m.dream.resistance.base
scoreboard players operation 0 temp += @s m.dream.resistance.modifier
scoreboard players operation @s m.dream.resistance = 0 temp
scoreboard players operation 0 temp = @s m.dream.weakness.base
scoreboard players operation 0 temp += @s m.dream.weakness.modifier
scoreboard players operation @s m.dream.weakness = 0 temp
execute unless score @s m.radiance.resistance.base matches ..0 unless score @s m.radiance.resistance.base matches 0.. run scoreboard players set @s m.radiance.resistance.base 0
execute unless score @s m.radiance.weakness.base matches ..0 unless score @s m.radiance.weakness.base matches 0.. run scoreboard players set @s m.radiance.weakness.base 0
scoreboard players operation 0 temp = @s m.radiance.resistance.base
scoreboard players operation 0 temp += @s m.radiance.resistance.modifier
scoreboard players operation @s m.radiance.resistance = 0 temp
scoreboard players operation 0 temp = @s m.radiance.weakness.base
scoreboard players operation 0 temp += @s m.radiance.weakness.modifier
scoreboard players operation @s m.radiance.weakness = 0 temp
execute unless score @s m.resonance.resistance.base matches ..0 unless score @s m.resonance.resistance.base matches 0.. run scoreboard players set @s m.resonance.resistance.base 0
execute unless score @s m.resonance.weakness.base matches ..0 unless score @s m.resonance.weakness.base matches 0.. run scoreboard players set @s m.resonance.weakness.base 0
scoreboard players operation 0 temp = @s m.resonance.resistance.base
scoreboard players operation 0 temp += @s m.resonance.resistance.modifier
scoreboard players operation @s m.resonance.resistance = 0 temp
scoreboard players operation 0 temp = @s m.resonance.weakness.base
scoreboard players operation 0 temp += @s m.resonance.weakness.modifier
scoreboard players operation @s m.resonance.weakness = 0 temp
execute unless score @s m.nightmare.resistance.base matches ..0 unless score @s m.nightmare.resistance.base matches 0.. run scoreboard players set @s m.nightmare.resistance.base 0
execute unless score @s m.nightmare.weakness.base matches ..0 unless score @s m.nightmare.weakness.base matches 0.. run scoreboard players set @s m.nightmare.weakness.base 0
scoreboard players operation 0 temp = @s m.nightmare.resistance.base
scoreboard players operation 0 temp += @s m.nightmare.resistance.modifier
scoreboard players operation @s m.nightmare.resistance = 0 temp
scoreboard players operation 0 temp = @s m.nightmare.weakness.base
scoreboard players operation 0 temp += @s m.nightmare.weakness.modifier
scoreboard players operation @s m.nightmare.weakness = 0 temp
execute unless score @s m.void.resistance.base matches ..0 unless score @s m.void.resistance.base matches 0.. run scoreboard players set @s m.void.resistance.base 0
execute unless score @s m.void.weakness.base matches ..0 unless score @s m.void.weakness.base matches 0.. run scoreboard players set @s m.void.weakness.base 0
scoreboard players operation 0 temp = @s m.void.resistance.base
scoreboard players operation 0 temp += @s m.void.resistance.modifier
scoreboard players operation @s m.void.resistance = 0 temp
scoreboard players operation 0 temp = @s m.void.weakness.base
scoreboard players operation 0 temp += @s m.void.weakness.modifier
scoreboard players operation @s m.void.weakness = 0 temp
execute unless score @s m.dissonance.resistance.base matches ..0 unless score @s m.dissonance.resistance.base matches 0.. run scoreboard players set @s m.dissonance.resistance.base 0
execute unless score @s m.dissonance.weakness.base matches ..0 unless score @s m.dissonance.weakness.base matches 0.. run scoreboard players set @s m.dissonance.weakness.base 0
scoreboard players operation 0 temp = @s m.dissonance.resistance.base
scoreboard players operation 0 temp += @s m.dissonance.resistance.modifier
scoreboard players operation @s m.dissonance.resistance = 0 temp
scoreboard players operation 0 temp = @s m.dissonance.weakness.base
scoreboard players operation 0 temp += @s m.dissonance.weakness.modifier
scoreboard players operation @s m.dissonance.weakness = 0 temp