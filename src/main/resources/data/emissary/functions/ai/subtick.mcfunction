#built using mc-build (https://github.com/mc-build/mc-build)

execute if score Emissary em.health matches ..1000 if score Idle em.value matches 1 if score Emissary em.phase matches 1 run function emissary:ai/phase/transition
execute if score Emissary em.health matches ..600 if score Idle em.value matches 1 if score Emissary em.phase matches 2 run function emissary:ai/phase/transition
execute if score Emissary em.health matches ..200 if score Idle em.value matches 1 if score Emissary em.phase matches 3 run function emissary:ai/phase/transition
execute if score Emissary em.health matches ..0 if score Idle em.value matches 1 if score Emissary em.phase matches 4 run function emissary:defeat
execute if score Emissary em.health matches ..1000 unless score MusicPhase em.phase matches 1.. run function emissary:ai/__generated__/block/730
execute if score Emissary em.health matches ..800 if score MusicPhase em.phase matches 1 run function emissary:ai/__generated__/block/731
execute if score Emissary em.health matches ..600 if score MusicPhase em.phase matches 2 run function emissary:ai/__generated__/block/732
execute if score Emissary em.health matches ..400 if score MusicPhase em.phase matches 3 run function emissary:ai/__generated__/block/733
execute if score Emissary em.health matches ..200 if score MusicPhase em.phase matches 4 run function emissary:ai/__generated__/block/734
execute at @s as @a[distance=..300,tag=!music.emissary] at @s run function emissary:ai/__generated__/block/735
execute if score Idle em.value matches 1 run scoreboard players remove AttackTimer em.value 1
execute unless score AttackTimer em.value matches 1.. unless score NoAttack em.value matches 1 run function emissary:attacks/perform
execute if score Flying em.value matches 1 run function emissary:ai/__generated__/block/736