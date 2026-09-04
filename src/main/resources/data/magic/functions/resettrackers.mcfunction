#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=m.dealt_damage.dream] run scoreboard players reset @s m.damage_dealt.dream
execute as @s[tag=m.dealt_damage.dream] run tag @s remove m.dealt_damage.dream
execute as @s[tag=m.dealt_damage.radiance] run scoreboard players reset @s m.damage_dealt.radiance
execute as @s[tag=m.dealt_damage.radiance] run tag @s remove m.dealt_damage.radiance
execute as @s[tag=m.dealt_damage.resonance] run scoreboard players reset @s m.damage_dealt.resonance
execute as @s[tag=m.dealt_damage.resonance] run tag @s remove m.dealt_damage.resonance
execute as @s[tag=m.dealt_damage.nightmare] run scoreboard players reset @s m.damage_dealt.nightmare
execute as @s[tag=m.dealt_damage.nightmare] run tag @s remove m.dealt_damage.nightmare
execute as @s[tag=m.dealt_damage.void] run scoreboard players reset @s m.damage_dealt.void
execute as @s[tag=m.dealt_damage.void] run tag @s remove m.dealt_damage.void
execute as @s[tag=m.dealt_damage.dissonance] run scoreboard players reset @s m.damage_dealt.dissonance
execute as @s[tag=m.dealt_damage.dissonance] run tag @s remove m.dealt_damage.dissonance
execute if score @s m.damage_dealt.dream matches 1.. run tag @s add m.dealt_damage.dream
execute if score @s m.damage_dealt.radiance matches 1.. run tag @s add m.dealt_damage.radiance
execute if score @s m.damage_dealt.resonance matches 1.. run tag @s add m.dealt_damage.resonance
execute if score @s m.damage_dealt.nightmare matches 1.. run tag @s add m.dealt_damage.nightmare
execute if score @s m.damage_dealt.void matches 1.. run tag @s add m.dealt_damage.void
execute if score @s m.damage_dealt.dissonance matches 1.. run tag @s add m.dealt_damage.dissonance
execute as @s[tag=m.taken_damage.dream] run scoreboard players reset @s m.damage_taken.dream
execute as @s[tag=m.taken_damage.dream] run tag @s remove m.taken_damage.dream
execute as @s[tag=m.taken_damage.radiance] run scoreboard players reset @s m.damage_taken.radiance
execute as @s[tag=m.taken_damage.radiance] run tag @s remove m.taken_damage.radiance
execute as @s[tag=m.taken_damage.resonance] run scoreboard players reset @s m.damage_taken.resonance
execute as @s[tag=m.taken_damage.resonance] run tag @s remove m.taken_damage.resonance
execute as @s[tag=m.taken_damage.nightmare] run scoreboard players reset @s m.damage_taken.nightmare
execute as @s[tag=m.taken_damage.nightmare] run tag @s remove m.taken_damage.nightmare
execute as @s[tag=m.taken_damage.void] run scoreboard players reset @s m.damage_taken.void
execute as @s[tag=m.taken_damage.void] run tag @s remove m.taken_damage.void
execute as @s[tag=m.taken_damage.dissonance] run scoreboard players reset @s m.damage_taken.dissonance
execute as @s[tag=m.taken_damage.dissonance] run tag @s remove m.taken_damage.dissonance
execute if score @s m.damage_taken.dream matches 1.. run tag @s add m.taken_damage.dream
execute if score @s m.damage_taken.radiance matches 1.. run tag @s add m.taken_damage.radiance
execute if score @s m.damage_taken.resonance matches 1.. run tag @s add m.taken_damage.resonance
execute if score @s m.damage_taken.nightmare matches 1.. run tag @s add m.taken_damage.nightmare
execute if score @s m.damage_taken.void matches 1.. run tag @s add m.taken_damage.void
execute if score @s m.damage_taken.dissonance matches 1.. run tag @s add m.taken_damage.dissonance
execute as @s[tag=m.killed.dream] run scoreboard players reset @s m.kills.dream
execute as @s[tag=m.killed.dream] run function magic:resetkillmarker
execute as @s[tag=m.killed.dream] run tag @s remove m.killed.dream
execute as @s[tag=m.killed.radiance] run scoreboard players reset @s m.kills.radiance
execute as @s[tag=m.killed.radiance] run function magic:resetkillmarker
execute as @s[tag=m.killed.radiance] run tag @s remove m.killed.radiance
execute as @s[tag=m.killed.resonance] run scoreboard players reset @s m.kills.resonance
execute as @s[tag=m.killed.resonance] run function magic:resetkillmarker
execute as @s[tag=m.killed.resonance] run tag @s remove m.killed.resonance
execute as @s[tag=m.killed.nightmare] run scoreboard players reset @s m.kills.nightmare
execute as @s[tag=m.killed.nightmare] run function magic:resetkillmarker
execute as @s[tag=m.killed.nightmare] run tag @s remove m.killed.nightmare
execute as @s[tag=m.killed.void] run scoreboard players reset @s m.kills.void
execute as @s[tag=m.killed.void] run function magic:resetkillmarker
execute as @s[tag=m.killed.void] run tag @s remove m.killed.void
execute as @s[tag=m.killed.dissonance] run scoreboard players reset @s m.kills.dissonance
execute as @s[tag=m.killed.dissonance] run function magic:resetkillmarker
execute as @s[tag=m.killed.dissonance] run tag @s remove m.killed.dissonance
execute if score @s m.kills.dream matches 1.. run tag @s add m.killed.dream
execute if score @s m.kills.radiance matches 1.. run tag @s add m.killed.radiance
execute if score @s m.kills.resonance matches 1.. run tag @s add m.killed.resonance
execute if score @s m.kills.nightmare matches 1.. run tag @s add m.killed.nightmare
execute if score @s m.kills.void matches 1.. run tag @s add m.killed.void
execute if score @s m.kills.dissonance matches 1.. run tag @s add m.killed.dissonance