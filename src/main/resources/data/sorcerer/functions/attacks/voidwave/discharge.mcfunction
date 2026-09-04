#built using mc-build (https://github.com/mc-build/mc-build)

function effects:shockwave/transmittance_first
particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute as @a[distance=..6] run function sorcerer:attacks/voidwave/hit