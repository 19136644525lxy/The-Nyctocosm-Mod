#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~ ~ 1 1 1 0 3 force
particle flame ~ ~ ~ 1 1 1 0.05 5 force
particle end_rod ~ ~ ~ 1 1 1 0.05 5 force
particle wax_on ~ ~ ~ 1 1 1 0 5 force
execute rotated ~ 0 if score RANGE em.value matches 10 run function emissary:attacks/phase2/celestial_smite/beamwave_big
execute rotated ~ 0 if score RANGE em.value matches 22 run function emissary:attacks/phase2/celestial_smite/beamwave_big
execute rotated ~ 0 if score RANGE em.value matches 58 run function emissary:attacks/phase2/celestial_smite/beamwave_big
execute rotated ~ 0 if score RANGE em.value matches 62 run function emissary:attacks/phase2/celestial_smite/beamwave
execute rotated ~ 0 if score RANGE em.value matches 75 run function emissary:attacks/phase2/celestial_smite/beamwave
function emissary:attacks/phase2/celestial_smite/raycast